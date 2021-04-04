package br.com.zupacademy.mercadolivre.utils;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.zupacademy.mercadolivre.models.Compra;

@Service
public class NotaFiscal implements ComunicaComSistemaExterno {

	@Override
	public void realizarComunicacaoComSistemaExterno(Compra compra) {
		RestTemplate template = new RestTemplate();
		Map<String, Long> request = Map.of("idCompra", compra.getId(), "idComprador", compra.getComprador().getId());
		template.postForEntity("http://localhost:8080/nota-fiscal", request, String.class);
	}
}
