package br.com.zupacademy.mercadolivre.utils;

import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.zupacademy.mercadolivre.models.Compra;

@Service
public class ComunicaComSistemaExternoService {

	private Set<ComunicaComSistemaExterno> comunicaComSistemaExternos;
	
	public ComunicaComSistemaExternoService(Set<ComunicaComSistemaExterno> comunicaComSistemaExternos) {
		this.comunicaComSistemaExternos = comunicaComSistemaExternos;
	}

	public boolean realizarComunicacaoComSistemaExterno(Compra compra) {
		
		if (compra.verificaSeATransacaoFoiProcessadaComSucesso()) {
			/*
			 * Nesse ponto o sistema se comunica com dois sistemas externos, Ranking e Nota Fiscal, ambos implementam
			 * o método realizarComunicacaoComSistemaExterno através da interface ComunicaComSistemaExterno.
			 * Portanto eu recebo injetado na classe uma lista do tipo ComunicaComSistemaExterno que monitora as suas implementações
			 * e através do método abaixo eu passo como argumento o objeto compra para a implementação.
			 * 
			 */
			comunicaComSistemaExternos.forEach(implementacao -> implementacao.realizarComunicacaoComSistemaExterno(compra));
			return true;			
		}
		return false;
	}

}
 