package br.com.zupacademy.mercadolivre.models.dto;

import br.com.zupacademy.mercadolivre.models.Opiniao;

public class OpiniaoDto {

	private Integer nota;
	private String titulo;
	private String descricao;

	public OpiniaoDto(Opiniao opiniao) {
		this.nota = opiniao.getNota();
		this.titulo = opiniao.getTitulo();
		this.descricao = opiniao.getDescricao();
	}

	public Integer getNota() {
		return nota;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

}
