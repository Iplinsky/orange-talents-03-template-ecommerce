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
			 * Nesse ponto o sistema se comunica com dois sistemas externos, Ranking e Nota Fiscal e, para a comunicação ser realizada
			 * as classes de serviço NotaFiscal e RankingVendedores implementam a interface ComunicaComSistemaExterno e elas são utilizadas 
			 * para fazer o redirecionamento usando o método  realizarComunicacaoComSistemaExterno provido pela interface. 
			 * Portanto eu recebo injetado dentro dessa classe uma lista de recurso ComunicaComSistemaExterno que monitora as suas implementações
			 * e através do método abaixo eu passo como argumento o objeto compra para cada uma das implementações.
			 * 
			 */
			comunicaComSistemaExternos.forEach(implementacao -> implementacao.realizarComunicacaoComSistemaExterno(compra));
			return true;			
		}
		return false;
	}

}
 