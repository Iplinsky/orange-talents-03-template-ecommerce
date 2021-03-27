package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.ProdutoFormDto;
import br.com.zupacademy.mercadolivre.repository.UsuarioRepository;
import br.com.zupacademy.mercadolivre.validator.restringeCaracteristicasIguaisValidator;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private UsuarioRepository usuarioRepository;

	public ProdutoController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@PersistenceContext
	private EntityManager em;

	@InitBinder
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

}
