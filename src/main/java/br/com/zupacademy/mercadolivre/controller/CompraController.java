package br.com.zupacademy.mercadolivre.controller;

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

import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.CompraFormDto;
import br.com.zupacademy.mercadolivre.utils.EnviaEmail;

@RestController
@RequestMapping("/compras")
public class CompraController {

	private EnviaEmail email;

	public CompraController(EnviaEmail email) {
		this.email = email;
	}

	@PersistenceContext
	private EntityManager em;

	@PostMapping
	@Transactional
	public String realizarCompra(@RequestBody @Valid CompraFormDto compraForm,
			@AuthenticationPrincipal Usuario usuarioComprador, UriComponentsBuilder uriBuilder) {

		Compra compra = compraForm.converter(usuarioComprador, em);
		em.persist(compra);
		email.enviaEmailNovaCompra(compra);

		return compra.urlRedirecionamento(uriBuilder);
	}

}
