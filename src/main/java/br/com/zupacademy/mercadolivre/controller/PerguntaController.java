package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.mercadolivre.models.Pergunta;
import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.PerguntaFormDto;
import br.com.zupacademy.mercadolivre.utils.EnviaEmail;

@RestController
public class PerguntaController {

	@PersistenceContext
	private EntityManager em;

	private EnviaEmail email;

	public PerguntaController(EnviaEmail email) {
		this.email = email;
	}

	@PostMapping("/produto/{id}/perguntas")
	@Transactional
	public ResponseEntity<?> cadastrarPergunta(@PathVariable("id") Long codProduto,
			@RequestBody @Valid PerguntaFormDto perguntaForm, @AuthenticationPrincipal Usuario usuarioLogado) {
		Assert.isTrue(codProduto != null, "O código do produto recebido pela requisição é inválido.");
		Assert.isTrue(usuarioLogado != null, "Usuário não atenticado ou inválido.");

		Produto produtoRelacionadoAPergunta = em.find(Produto.class, codProduto);
		Pergunta pergunta = new Pergunta(perguntaForm.getTitulo(), usuarioLogado, produtoRelacionadoAPergunta);

		produtoRelacionadoAPergunta.adicionarPeguntaAoProduto(pergunta);
		em.persist(produtoRelacionadoAPergunta);

		email.enviaEmailNovaPergunta(pergunta);

		return ResponseEntity.ok().build();
	}

}
