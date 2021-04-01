package br.com.zupacademy.mercadolivre.controller;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.mercadolivre.enumerators.Gateway;
import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.CompraFormDto;

@RestController
@RequestMapping("/compras")
public class CompraController {

	@PersistenceContext
	private EntityManager em;

	@PostMapping
	@Transactional
	public String realizarCompra(@RequestBody @Valid CompraFormDto compraForm,
			@AuthenticationPrincipal Usuario usuarioComprador, UriComponentsBuilder uriBuilder) {

		Compra compra = compraForm.converter(usuarioComprador, em);
		em.persist(compra);

		if (compraForm.getGateway().equals(Gateway.PAGSEGURO)) {
			URI uriRetornoPagSeguro = uriBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(compra.getId()).toUri();
			return String.format("pagseguro.com?returnId=%s&redirectUrl=", compra.getId(), uriRetornoPagSeguro);

		} else {
			URI uriRetornoPayPal = uriBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toUri();
			return String.format("paypal.com?buyerId=%s&redirectUrl=%s", compra.getId(), uriRetornoPayPal);
		}
	}

}
