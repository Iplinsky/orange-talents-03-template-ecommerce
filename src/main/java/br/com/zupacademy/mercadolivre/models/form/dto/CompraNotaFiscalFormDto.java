package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.NotNull;

public class CompraNotaFiscalFormDto {

	@NotNull
	private Long idCompra;

	@NotNull
	private Long idComprador;

	public CompraNotaFiscalFormDto(@NotNull Long idCompra, @NotNull Long idComprador) {
		this.idCompra = idCompra;
		this.idComprador = idComprador;
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public Long getIdComprador() {
		return idComprador;
	}

}
