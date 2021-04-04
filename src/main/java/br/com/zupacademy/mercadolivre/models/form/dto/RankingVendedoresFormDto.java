package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.NotNull;

public class RankingVendedoresFormDto {

	@NotNull
	private Long idCompra;

	@NotNull
	private Long idDonoDoProduto;

	public RankingVendedoresFormDto(Long idCompra, Long idDonoDoProduto) {
		this.idCompra = idCompra;
		this.idDonoDoProduto = idDonoDoProduto;
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public Long getIdDonoDoProduto() {
		return idDonoDoProduto;
	}

}
