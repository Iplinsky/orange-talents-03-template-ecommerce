package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import br.com.zupacademy.mercadolivre.enumerators.StatusPagamento;
import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Transacao;
import br.com.zupacademy.mercadolivre.utils.RecebeGatewayRetornaTransacao;

public class ProcessamentoDePagamentoPayPalFormDto implements RecebeGatewayRetornaTransacao {

	@NotBlank
	private String idTransacao;

	@Min(0) // ERRO
	@Max(1) // SUCESSO
	private int status;

	public ProcessamentoDePagamentoPayPalFormDto(@NotBlank String idTransacao, @Min(0) @Max(1) int status) {
		this.idTransacao = idTransacao;
		this.status = status;
	}

	@Override
	public Transacao coverteParaTransacao(Compra compra) {
		return new Transacao(idTransacao, this.status == 0 ? StatusPagamento.ERRO : StatusPagamento.SUCESSO, compra);
	}

	public int getStatus() {
		return status;
	}

}
