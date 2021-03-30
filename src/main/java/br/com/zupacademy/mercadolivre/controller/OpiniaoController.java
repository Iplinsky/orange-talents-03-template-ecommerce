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

import br.com.zupacademy.mercadolivre.models.Opiniao;
import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.OpiniaoFormDto;

@RestController
public class OpiniaoController {

	@PersistenceContext
	private EntityManager em;

	@PostMapping("/produtos/{id}/opiniao")
	@Transactional
	public ResponseEntity<?> adicionarOpiniaoAoProduto(@PathVariable("id") Long codProduto,
			@RequestBody @Valid OpiniaoFormDto opiniaoForm, @AuthenticationPrincipal Usuario usuarioLogado) {

		Produto produto = em.find(Produto.class, codProduto);

		/*
		 * Caso o Produto não seja localizado será retornado 404 Not Found!
		 */
		if (produto.equals(null))
			return ResponseEntity.notFound().build();

		Opiniao opiniao = opiniaoForm.converter(usuarioLogado, produto);

		produto.adicionaOpiniao(opiniao);
		em.merge(produto);

		return ResponseEntity.ok().build();
	}

}
