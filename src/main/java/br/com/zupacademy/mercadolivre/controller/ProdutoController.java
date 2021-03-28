package br.com.zupacademy.mercadolivre.controller;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.zupacademy.mercadolivre.repository.UsuarioRepository;
import br.com.zupacademy.mercadolivre.utils.UploaderFake;
import br.com.zupacademy.mercadolivre.validator.restringeCaracteristicasIguaisValidator;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private UsuarioRepository usuarioRepository;
	private UploaderFake uploaderFake;

	public ProdutoController(UsuarioRepository usuarioRepository, UploaderFake uploaderFake) {
		this.usuarioRepository = usuarioRepository;
		this.uploaderFake = uploaderFake;
	}

	@PersistenceContext
	private EntityManager em;

	@InitBinder(value = "ProdutoFormDto")
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(new restringeCaracteristicasIguaisValidator());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid ProdutoFormDto form) {
		Usuario donoDoProduto = usuarioRepository.findByEmail("thiago@gmail.com").get();
		Produto produto = form.converter(em, donoDoProduto);
		em.persist(produto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/imagens")
	@Transactional
	public ResponseEntity<?> adicionaImagemAoProduto(@PathVariable("id") Long codProduto,
			@Valid ImagemFormDto imagemForm) {
		Produto produto = em.find(Produto.class, codProduto);
		Usuario donoDoProduto = usuarioRepository.findByEmail("thiago@gmail.com").get();

		if (!produto.verificaSeOProdutoPertenceAoUsuario(donoDoProduto)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		Set<String> listaDeLinks = uploaderFake.envia(imagemForm.getListaImagens());
		produto.relacionaImagens(listaDeLinks);
		em.merge(produto);

		return ResponseEntity.ok().build();
	}

}
