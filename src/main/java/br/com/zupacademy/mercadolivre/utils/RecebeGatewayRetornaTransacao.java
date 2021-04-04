package br.com.zupacademy.mercadolivre.utils;

import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Transacao;

public interface RecebeGatewayRetornaTransacao {

	public Transacao coverteParaTransacao(Compra compra);

}
