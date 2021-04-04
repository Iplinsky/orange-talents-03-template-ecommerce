package br.com.zupacademy.mercadolivre.enumerators;

import javax.validation.constraints.NotNull;

public enum StatusPagamentoPagSeguro {
	ERRO, SUCESSO;

	@NotNull
	public StatusPagamento padronizaStatusSistema() {
		if (this.equals(StatusPagamentoPagSeguro.ERRO)) {
			return StatusPagamento.ERRO;
		}
		return StatusPagamento.SUCESSO;
	}
}
