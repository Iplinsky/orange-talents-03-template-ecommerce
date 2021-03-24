package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.mercadolivre.models.Categoria;
import br.com.zupacademy.mercadolivre.models.form.dto.CategoriaFormDto;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@PersistenceContext
	private EntityManager entityManager;

	@PostMapping
	@Transactional
	public void cadastro(@RequestBody @Valid CategoriaFormDto categoriaFormDto) {
		Categoria categoria = categoriaFormDto.converter(entityManager);
		entityManager.persist(categoria);
	}

}
 