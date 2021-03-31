package br.com.zupacademy.mercadolivre.models.dto;

import br.com.zupacademy.mercadolivre.models.Pergunta;

public class PerguntaDto {

	private String titulo;

	public PerguntaDto(Pergunta pergunta) {
		this.titulo = pergunta.getTitulo();
	}

	public String getTitulo() {
		return titulo;
	}
	
}
