package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.mercadolivre.enumerators.StatusPagamentoPagSeguro;
import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.CompraFormDto;
import br.com.zupacademy.mercadolivre.models.form.dto.ProcessamentoDePagamentoPagSeguroFormDto;
import br.com.zupacademy.mercadolivre.models.form.dto.ProcessamentoDePagamentoPayPalFormDto;
import br.com.zupacademy.mercadolivre.utils.ComunicaComSistemaExternoService;
import br.com.zupacademy.mercadolivre.utils.EnviaEmail;
import br.com.zupacademy.mercadolivre.utils.RecebeGatewayRetornaTransacao;

@RestController
public class CompraController {

	private EnviaEmail email;

	private ComunicaComSistemaExternoService comunicaComSistemaExternosService;

	public CompraController(EnviaEmail email, ComunicaComSistemaExternoService comunicaComSistemaExternosService) {
		this.email = email;
		this.comunicaComSistemaExternosService = comunicaComSistemaExternosService;
	}

	@PersistenceContext
	private EntityManager em;

	@PostMapping("/compras")
	@Transactional
	public String realizarCompra(@RequestBody @Valid CompraFormDto compraForm,
			@AuthenticationPrincipal Usuario usuarioComprador, UriComponentsBuilder uriBuilder) {

		Compra compra = compraForm.converter(usuarioComprador, em);
		em.persist(compra);
		email.enviaEmailNovaCompra(compra);

		return compra.urlRedirecionamento(uriBuilder);
	}

	@PostMapping("/retorno-pagseguro/{id}")
	@Transactional
	public ResponseEntity<StatusPagamentoPagSeguro> retornoProcessamentoPagSeguro(@PathVariable("id") Long idCompra,
			@Valid ProcessamentoDePagamentoPagSeguroFormDto formProcessamentoPagSeguro) {
		processaRetornoDoPagamento(idCompra, formProcessamentoPagSeguro);

		return ResponseEntity.ok(formProcessamentoPagSeguro.getStatusPagamento());
	}

	@PostMapping("/retorno-paypal/{id}")
	@Transactional
	public ResponseEntity<Integer> retornoProcessamentoPayPal(@PathVariable("id") Long idCompra,
			@Valid ProcessamentoDePagamentoPayPalFormDto formProcessamentoPayPal) {
		processaRetornoDoPagamento(idCompra, formProcessamentoPayPal);

		return ResponseEntity.ok(formProcessamentoPayPal.getStatus());
	}

	private void processaRetornoDoPagamento(Long idCompra, @Valid RecebeGatewayRetornaTransacao retornaGatewayParaTransacao) {
		Compra compra = em.find(Compra.class, idCompra);
		compra.adicionarNaListaDeTransaoes(retornaGatewayParaTransacao);
		em.merge(compra);
		
		if (!comunicaComSistemaExternosService.realizarComunicacaoComSistemaExterno(compra)) {
			email.enviaEmailFalhaNaConclusaoDeCompra(compra);
		} 
		
		email.enviaEmailConclusaoDeCompraComSucesso(compra);		
	}
}
