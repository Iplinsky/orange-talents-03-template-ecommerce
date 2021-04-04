package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.mercadolivre.enumerators.StatusPagamentoPagSeguro;
import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Transacao;
import br.com.zupacademy.mercadolivre.utils.RecebeGatewayRetornaTransacao;
import br.com.zupacademy.mercadolivre.validator.UniqueValue;

public class ProcessamentoDePagamentoPagSeguroFormDto implements RecebeGatewayRetornaTransacao {

	@UniqueValue(domain = Transacao.class, field = "idTransacao")
	@NotBlank
	private String idTransacao;

	@NotNull
	private StatusPagamentoPagSeguro statusPagamento;

	public ProcessamentoDePagamentoPagSeguroFormDto(@NotBlank String idTransacao,
			@NotNull StatusPagamentoPagSeguro statusPagamento) {
		this.idTransacao = idTransacao;
		this.statusPagamento = statusPagamento;
	}

	@Override
	public Transacao coverteParaTransacao(Compra compra) {
		return new Transacao(idTransacao, statusPagamento.padronizaStatusSistema(), compra);
	}

	public StatusPagamentoPagSeguro getStatusPagamento() {
		return this.statusPagamento;
	}

}
