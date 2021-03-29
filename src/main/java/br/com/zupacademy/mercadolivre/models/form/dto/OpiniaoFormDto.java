package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.zupacademy.mercadolivre.models.Opiniao;
import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;

public class OpiniaoFormDto {

	@Min(value = 1)
	@Max(value = 5)
	private Integer nota;

	@NotBlank
	private String titulo;

	@NotBlank
	@Length(max = 500)
	private String descricao;

	public OpiniaoFormDto(@Min(1) @Max(5) Integer nota, @NotBlank String titulo,
			@NotBlank @Length(max = 500) String descricao) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
	}

	public Opiniao converter(@NotNull @Valid Usuario usuarioLogado, @NotNull @Valid Produto produto) {
		return new Opiniao(nota, titulo, descricao, usuarioLogado, produto);
	}

}
