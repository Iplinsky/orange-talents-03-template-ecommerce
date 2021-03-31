package br.com.zupacademy.mercadolivre.models.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import br.com.zupacademy.mercadolivre.models.Produto;

public class ProdutoDto {

	private String nome;
	private BigDecimal valor;
	private Integer quantidadeDisponivel;
	private String descricao;
	private String nomeCategoria;
	private String emailDonoDoProduto;
	private Set<CaracteristicaDto> caracteristicas;
	private Set<Map<String, String>> listaDeOpinioes;
	private Set<ImagemDto> linkImagens;
	private SortedSet<String> titulosDePerguntas;
	private double mediaDeNotas;
	private int totalDeNotasDoProduto;

	public ProdutoDto(Produto produto) {
		this.nome = produto.getNome();
		this.valor = produto.getValor();
		this.quantidadeDisponivel = produto.getQuantidadeDisponivel();
		this.descricao = produto.getDescricao();
		this.nomeCategoria = produto.getCategoria().getNome();
		this.emailDonoDoProduto = produto.getDonoDoProduto().getUsername();
		this.caracteristicas = produto.mappingToCaracteristicas(CaracteristicaDto::new);
		this.listaDeOpinioes = produto.mappingToOpinioes(opiniao -> {
			return Map.of("titulo", opiniao.getTitulo(), "descricao", opiniao.getDescricao());
		});
		this.linkImagens = produto.mappingToImagens(ImagemDto::new);
		this.titulosDePerguntas = produto.mappingToPerguntas(pergunta -> pergunta.getTitulo());
		this.mediaDeNotas = produto.mappingToOpinioes(opiniao -> opiniao.getNota()).stream().mapToInt(nota -> nota).average().orElse(0.0);
		this.totalDeNotasDoProduto = produto.mappingToOpinioes(opiniao -> opiniao.getNota()).size();
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Integer getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public String getEmailDonoDoProduto() {
		return emailDonoDoProduto;
	}

	public Set<CaracteristicaDto> getCaracteristicas() {
		return caracteristicas;
	}

	public Set<ImagemDto> getLinkImagens() {
		return linkImagens;
	}

	public Set<Map<String, String>> getListaDeOpinioes() {
		return listaDeOpinioes;
	}

	public SortedSet<String> getTitulosDePerguntas() {
		return titulosDePerguntas;
	}

	public double getMediaDeNotas() {
		return mediaDeNotas;
	}

	public int getTotalDeNotasDoProduto() {
		return totalDeNotasDoProduto;
	}

	public static ProdutoDto converter(Produto produto) {
		return new ProdutoDto(produto);
	}

}
