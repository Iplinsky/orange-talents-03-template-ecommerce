package br.com.zupacademy.mercadolivre.models.dto;

import br.com.zupacademy.mercadolivre.models.Imagem;

public class ImagemDto {

	private String link;

	public ImagemDto(Imagem imagem) {
		this.link = imagem.getLink();
	}

	public String getLink() {
		return link;
	}

}
