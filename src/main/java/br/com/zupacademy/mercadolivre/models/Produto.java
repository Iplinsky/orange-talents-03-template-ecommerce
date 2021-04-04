package br.com.zupacademy.mercadolivre.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import br.com.zupacademy.mercadolivre.models.form.dto.CaracteristicaFormDto;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;

	@NotNull
	@Positive
	private BigDecimal valor;

	@NotNull
	@Min(value = 0)
	private Integer quantidadeDisponivel;

	@NotBlank
	@Length(max = 1000)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	@NotNull
	@Valid
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "donoDoProduto_id", nullable = false)
	@NotNull
	@Valid
	private Usuario donoDoProduto;

	@Size(min = 3)
	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	private Set<Caracteristica> caracteristicas = new HashSet<Caracteristica>();

	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<Imagem> imagens = new HashSet<Imagem>();

	@OneToMany(mappedBy = "produtoOpinado", cascade = CascadeType.PERSIST)
	private Set<Opiniao> listaDeOpinioes = new HashSet<Opiniao>();

	@OneToMany(mappedBy = "produtoPergunta", cascade = CascadeType.PERSIST)
	@OrderBy("titulo asc")
	private SortedSet<Pergunta> listaDePergunas = new TreeSet<Pergunta>();

	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	private List<Compra> compras = new ArrayList<Compra>();

	@SuppressWarnings("unused")
	private LocalDateTime instanteDoCadastro = LocalDateTime.now();

	@Deprecated
	public Produto() {
	}

	public Produto(@NotBlank String nome, @Positive BigDecimal valor, @Min(0) Integer quantidadeDisponivel,
			@NotBlank @Length(max = 1000) String descricao, @NotNull @Valid Categoria categoria,
			@NotNull @Valid Usuario donoDoProduto,
			@Size(min = 3) @Valid List<CaracteristicaFormDto> listaDeCaracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.setQuantidadeDisponivel(quantidadeDisponivel);
		this.descricao = descricao;
		this.categoria = categoria;
		this.donoDoProduto = donoDoProduto;
		this.caracteristicas.addAll(listaDeCaracteristicas.stream().map(caracteristica -> caracteristica.converte(this))
				.collect(Collectors.toSet()));

		Assert.isTrue(this.caracteristicas.size() >= 3, "Um produto precisa ter ao menos 3 características!");
	}

	public Long getId() {
		return id;
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

	public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public String getDescricao() {
		return descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Usuario getDonoDoProduto() {
		return donoDoProduto;
	}

	public Set<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	public Set<Imagem> getImagens() {
		return imagens;
	}

	public Set<Opiniao> getListaDeOpinioes() {
		return listaDeOpinioes;
	}

	public Set<Pergunta> getListaDePergunas() {
		return listaDePergunas;
	}

	public LocalDateTime getInstanteDoCadastro() {
		return instanteDoCadastro;
	}

	public void relacionaImagens(Set<String> listaDeLinks) {
		this.imagens.addAll(listaDeLinks.stream().map(link -> new Imagem(link, this)).collect(Collectors.toSet()));
	}

	public void adicionaOpiniao(Opiniao opiniao) {
		this.listaDeOpinioes.add(opiniao);
	}

	public void adicionarPeguntaAoProduto(Pergunta pergunta) {
		this.listaDePergunas.add(pergunta);
	}

	public boolean verificaSeOProdutoPertenceAoUsuario(Usuario donoDoProduto) {
		if (this.donoDoProduto.getId() == donoDoProduto.getId()) {
			return true;
		}
		return false;
	}

	public <T> Set<T> mappingToCaracteristicas(Function<Caracteristica, T> mappingFunction) {
		return this.caracteristicas.stream().map(mappingFunction).collect(Collectors.toSet());
	}

	public <T> Set<T> mappingToImagens(Function<Imagem, T> mappingFunction) {
		return this.imagens.stream().map(mappingFunction).collect(Collectors.toSet());
	}

	public <T extends Comparable<T>> SortedSet<T> mappingToPerguntas(Function<Pergunta, T> mappingFunction) {
		return this.listaDePergunas.stream().map(mappingFunction).collect(Collectors.toCollection(TreeSet::new));
	}

	public void abateEstoqueDoProduto(@Positive @NotNull Integer quantidadeDeItensParaAbater) {
		Assert.isTrue(quantidadeDeItensParaAbater > 0,
				"A quantidade de itens para serem abatidos deve ser maior que zero!");
		Assert.isTrue(this.quantidadeDisponivel >= quantidadeDeItensParaAbater, String
				.format("A quantidade em estoque não é suficiente para atender o pedido do produto: %s", this.nome));

		this.quantidadeDisponivel -= quantidadeDeItensParaAbater;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", valor=" + valor + ", descricao=" + descricao + "]";
	}

}
