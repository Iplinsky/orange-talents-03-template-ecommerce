package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

import br.com.zupacademy.mercadolivre.models.Categoria;
import br.com.zupacademy.mercadolivre.validator.CheckId;
import br.com.zupacademy.mercadolivre.validator.UniqueValue;

public class CategoriaFormDto {

	@NotBlank
	@UniqueValue(field = "nome", domain = Categoria.class)
	private String nome;

	@CheckId(field = "id", domain = Categoria.class)
	private Long idCategoriaMae;

	public CategoriaFormDto(@NotBlank String nome, Long idCategoriaMae) {
		this.nome = nome;
		this.idCategoriaMae = idCategoriaMae;
	}

	public Categoria converter(EntityManager entityManager) {
		/* A Categoria pode conter ou não uma Categoria Mãe */
		Categoria categoriaMae = idCategoriaMae != null ? entityManager.find(Categoria.class, idCategoriaMae) : null;
		return new Categoria(nome, categoriaMae);
	}
}
