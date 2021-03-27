package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import br.com.zupacademy.mercadolivre.models.Caracteristica;
import br.com.zupacademy.mercadolivre.models.Produto;

public class CaracteristicaFormDto {

	@NotBlank
	private String nome;

	@NotBlank
	private String descricao;

	public CaracteristicaFormDto(@NotBlank String nome, @NotBlank String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public String getNome() {
		return this.nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Caracteristica converte(@NotNull @Valid Produto produto) {
		return new Caracteristica(nome, descricao, produto);
	}

}
