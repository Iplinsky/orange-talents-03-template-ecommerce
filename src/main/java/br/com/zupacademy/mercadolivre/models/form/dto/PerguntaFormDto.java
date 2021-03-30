package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.NotBlank;

public class PerguntaFormDto {

	@NotBlank(message = "É necessário um título para a pergunta!")
	private String titulo;

	public String getTitulo() {
		return titulo;
	}

}
