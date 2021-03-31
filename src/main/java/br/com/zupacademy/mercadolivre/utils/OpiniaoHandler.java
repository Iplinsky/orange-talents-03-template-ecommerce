package br.com.zupacademy.mercadolivre.utils;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.zupacademy.mercadolivre.models.Opiniao;

/**
 * Classe utilizada para realizar manipulações com as opiniões declaradas ao produto.
 *
 */
public class OpiniaoHandler {

	private Set<Opiniao> listaDeOpinioes;

	public OpiniaoHandler(Set<Opiniao> listaDeOpinioes) {
		this.listaDeOpinioes = listaDeOpinioes;
	}

	public <T> Set<T> mappingToOpinioes(Function<Opiniao, T> mappingFunction) {
		return this.listaDeOpinioes.stream().map(mappingFunction).collect(Collectors.toSet());
	}

	public double mediaDeNotas() {
		/*
		 * Até então o sistema não obriga o usuário a dar uma nota ao produto, então podem ocorrer casos em que a nota da opinião será NULL.
		 * Por enquanto se não houver nota, o sistema reconhecerá a nota 5 para dar prosseguimento ao cálculo de média.
		 * 
		 */
		return mappingToOpinioes(opiniao -> opiniao.getNota() != null ? opiniao.getNota() : 5).stream().mapToInt(nota -> nota).average().orElse(0.0);
	}

	public int quantidadeTotalDeOpinioes() {
		return this.listaDeOpinioes.size();
	}

}
