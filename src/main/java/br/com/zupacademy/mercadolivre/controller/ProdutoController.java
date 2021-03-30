package br.com.zupacademy.mercadolivre.controller;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.ImagemFormDto;
import br.com.zupacademy.mercadolivre.models.form.dto.ProdutoFormDto;
import br.com.zupacademy.mercadolivre.utils.FalsoUpload;
import br.com.zupacademy.mercadolivre.validator.restringeCaracteristicasIguaisValidator;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private FalsoUpload falsoUpload;

	public ProdutoController(FalsoUpload falsoUpload) {
		this.falsoUpload = falsoUpload;
	}

	@PersistenceContext
	private EntityManager em;

	@InitBinder(value = "ProdutoFormDto")
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(new restringeCaracteristicasIguaisValidator());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrarProduto(@RequestBody @Valid ProdutoFormDto produtoForm,
			@AuthenticationPrincipal Usuario usuarioLogado) {
		Produto produto = produtoForm.converter(em, usuarioLogado);
		em.persist(produto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/imagens")
	@Transactional
	public ResponseEntity<?> adicionaImagemAoProduto(@PathVariable("id") Long codProduto,
			@Valid ImagemFormDto imagemForm, @AuthenticationPrincipal Usuario usuarioLogado) {
		Produto produto = em.find(Produto.class, codProduto);
		/*
		 * Caso o Produto não seja localizado será retornado 404 Not Found!
		 */
		if (produto.equals(null))
			return ResponseEntity.notFound().build();

		if (!produto.verificaSeOProdutoPertenceAoUsuario(usuarioLogado)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		// Gerando um link fictício
		Set<String> listaDeLinks = falsoUpload.envia(imagemForm.getListaImagens());
		produto.relacionaImagens(listaDeLinks);
		em.merge(produto);

		return ResponseEntity.ok().build();
	}

}
