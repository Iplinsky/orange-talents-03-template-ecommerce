package br.com.zupacademy.mercadolivre.models.dto;

public class TokenDto {

	public String token;
	public String tipo;

	public TokenDto(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

}