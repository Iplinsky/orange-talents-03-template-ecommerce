package br.com.zupacademy.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.models.form.dto.RankingVendedoresFormDto;

@RestController
public class RankingVendedoresController {

	@PersistenceContext
	private EntityManager em;

	@PostMapping("/ranking")
	public void comunicaComSistemaDeRankingDosVendedores(@RequestBody @Valid RankingVendedoresFormDto form)
			throws InterruptedException {

		Compra compra = em.find(Compra.class, form.getIdCompra());
		Usuario donoDoProduto = em.find(Usuario.class, form.getIdDonoDoProduto());

		System.out.println(String.format(
				"A comunicação com o sistema de Ranking dos Vendedores foi realizada - %s / Dono do Produto: ", compra,
				donoDoProduto.getUsername()));

		Thread.sleep(150);
	}

}
