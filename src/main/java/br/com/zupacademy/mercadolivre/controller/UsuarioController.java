package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.UsuarioFormDto;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@PersistenceContext
	EntityManager em;

	@PostMapping
	@Transactional
	public void cadastro(@RequestBody @Valid UsuarioFormDto form) {
		Usuario usuario = form.converter();
		em.persist(usuario);
	}
}
