package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.CompraNotaFiscalFormDto;

@RestController
public class NotaFiscalController {

	@PersistenceContext
	private EntityManager em;

	@PostMapping("/nota-fiscal")
	public void comunicaComSetorNotaFiscal(@RequestBody @Valid CompraNotaFiscalFormDto dtoNf) throws InterruptedException {

		Compra compra = em.find(Compra.class, dtoNf.getIdCompra());
		@SuppressWarnings("unused")
		Usuario comprador = em.find(Usuario.class, dtoNf.getIdComprador());

		System.out.println(
				String.format("A comunicação com o Setor de Nota Fiscal foi realizada - %s", compra.toString()));
		Thread.sleep(150);
	}

}
