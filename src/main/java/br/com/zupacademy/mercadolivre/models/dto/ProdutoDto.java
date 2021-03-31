package br.com.zupacademy.mercadolivre.models.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.utils.OpiniaoHandler;

public class ProdutoDto {

	private String nome;
	private BigDecimal valor;
	private Integer quantidadeDisponivel;
	private String descricao;
	private String nomeCategoria;
	private String emailDonoDoProduto;
	private Set<CaracteristicaDto> caracteristicas;
	private Set<ImagemDto> linkImagens;
	private SortedSet<String> titulosDePerguntas;
	private Set<Map<String, String>> listaDeOpinioes;
	private double mediaDeNotas;
	private int totalDeOpinioes;

	public ProdutoDto(Produto produto) {
		this.nome = produto.getNome();
		this.valor = produto.getValor();
		this.quantidadeDisponivel = produto.getQuantidadeDisponivel();
		this.descricao = produto.getDescricao();
		this.nomeCategoria = produto.getCategoria().getNome();
		this.emailDonoDoProduto = produto.getDonoDoProduto().getUsername();
		this.caracteristicas = produto.mappingToCaracteristicas(CaracteristicaDto::new);
		this.linkImagens = produto.mappingToImagens(ImagemDto::new);
		this.titulosDePerguntas = produto.mappingToPerguntas(pergunta -> pergunta.getTitulo());
		// Período de código que trata as informações relacionadas à Opinião.
		OpiniaoHandler manipulaOpiniao = new OpiniaoHandler(produto.getListaDeOpinioes());
		this.listaDeOpinioes = manipulaOpiniao.mappingToOpinioes(opiniao -> {
			return Map.of("titulo", opiniao.getTitulo(), "descricao", opiniao.getDescricao());
		});
		this.mediaDeNotas = manipulaOpiniao.mediaDeNotas();
		this.totalDeOpinioes = manipulaOpiniao.quantidadeTotalDeOpinioes();
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

	public int getTotalDeOpinioes() {
		return totalDeOpinioes;
	}

	public static ProdutoDto converter(Produto produto) {
		return new ProdutoDto(produto);
	}

}
