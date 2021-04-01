package br.com.zupacademy.mercadolivre.enumerators;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.mercadolivre.models.Compra;

public enum Gateway {
	PAYPAL {
		@Override
		public String criaUrlRetorno(Compra compra, UriComponentsBuilder uriBuilder) {
			URI uriRetornoPayPal = uriBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toUri();
			return String.format("paypal.com?buyerId=%s&redirectUrl=%s", compra.getId(), uriRetornoPayPal);
		}
	},
	PAGSEGURO {
		@Override
		public String criaUrlRetorno(Compra compra, UriComponentsBuilder uriBuilder) {
			URI uriRetornoPagSeguro = uriBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(compra.getId()).toUri();
			return String.format("pagseguro.com?returnId=%s&redirectUrl=%S", compra.getId(), uriRetornoPagSeguro);
		}
	};

	public abstract String criaUrlRetorno(Compra compra, UriComponentsBuilder uriBuilder);
	
}
