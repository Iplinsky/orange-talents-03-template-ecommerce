package br.com.zupacademy.mercadolivre.models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.mercadolivre.enumerators.Gateway;
import br.com.zupacademy.mercadolivre.enumerators.StatusCompra;
import br.com.zupacademy.mercadolivre.enumerators.StatusPagamento;
import br.com.zupacademy.mercadolivre.utils.RecebeGatewayRetornaTransacao;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Positive
	@NotNull
	private Integer quantidadeDeItens;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	@NotNull
	@Valid
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "comprador_id")
	@NotNull
	@Valid
	private Usuario comprador;

	@NotNull
	private BigDecimal valorAtualDoProduto;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gateway gateway;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusCompra statusCompra;

	@Valid
	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<Transacao>();

	@Deprecated
	public Compra() {
	}

	public Compra(@Positive @NotNull Integer quantidadeDeItens, @NotNull @Valid Produto produto,
			@NotNull @Valid Usuario comprador, @NotNull BigDecimal valorAtualDoProduto, @NotNull Gateway gateway) {
		this.quantidadeDeItens = quantidadeDeItens;
		this.produto = produto;
		this.comprador = comprador;
		this.valorAtualDoProduto = valorAtualDoProduto;
		this.gateway = gateway;
		this.statusCompra = StatusCompra.INICIADA;
	}

	public Long getId() {
		return this.id;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public Produto getProduto() {
		return produto;
	}

	public StatusCompra getStatusCompra() {
		return statusCompra;
	}

	public Set<Transacao> getTransacoes() {
		return transacoes;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public String urlRedirecionamento(UriComponentsBuilder uriBuilder) {
		return this.getGateway().criaUrlRetorno(this, uriBuilder);
	}

	public void adicionarNaListaDeTransaoes(@Valid RecebeGatewayRetornaTransacao valor) {
		Transacao transacao = valor.coverteParaTransacao(this);
		validaTransacao(transacao);
		this.getTransacoes().add(transacao);
	}

	private void validaTransacao(Transacao transacao) {
		Assert.isTrue(!this.getTransacoes().contains(transacao),
				String.format("O sistema já realizou o processamento da transação: %s", transacao.getIdTransacao()));
		Assert.isTrue(RetornaListaTransacaoConcluidaComSucesso().size() <= 1,
				"Só pode haver uma transação concluída com sucesso!");
		Assert.isTrue(RetornaListaTransacaoConcluidaComSucesso().isEmpty(),
				"O sistema já processou essa transação e ela foi concluída com sucesso.");
	}

	private Set<Transacao> RetornaListaTransacaoConcluidaComSucesso() {
		Set<Transacao> listaTransacaoSucesso = this.getTransacoes().stream()
				.filter(T -> T.getStatusPagamento().equals(StatusPagamento.SUCESSO)).collect(Collectors.toSet());
		return listaTransacaoSucesso;
	}

	public boolean verificaSeATransacaoFoiProcessadaComSucesso() {
		return !RetornaListaTransacaoConcluidaComSucesso().isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getComprador() == null) ? 0 : getComprador().hashCode());
		result = prime * result + ((getGateway() == null) ? 0 : getGateway().hashCode());
		result = prime * result + ((getProduto() == null) ? 0 : getProduto().hashCode());
		result = prime * result + ((quantidadeDeItens == null) ? 0 : quantidadeDeItens.hashCode());
		result = prime * result + ((getStatusCompra() == null) ? 0 : getStatusCompra().hashCode());
		result = prime * result + ((valorAtualDoProduto == null) ? 0 : valorAtualDoProduto.hashCode());
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
		Compra other = (Compra) obj;
		if (getComprador() == null) {
			if (other.getComprador() != null)
				return false;
		} else if (!getComprador().equals(other.getComprador()))
			return false;
		if (getGateway() != other.getGateway())
			return false;
		if (getProduto() == null) {
			if (other.getProduto() != null)
				return false;
		} else if (!getProduto().equals(other.getProduto()))
			return false;
		if (quantidadeDeItens == null) {
			if (other.quantidadeDeItens != null)
				return false;
		} else if (!quantidadeDeItens.equals(other.quantidadeDeItens))
			return false;
		if (getStatusCompra() != other.getStatusCompra())
			return false;
		if (valorAtualDoProduto == null) {
			if (other.valorAtualDoProduto != null)
				return false;
		} else if (!valorAtualDoProduto.equals(other.valorAtualDoProduto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", quantidadeDeItens=" + quantidadeDeItens + ", valorAtualDoProduto="
				+ valorAtualDoProduto + ", gateway=" + getGateway() + ", statusCompra=" + getStatusCompra() + ", "
				+ produto.toString() + "]";
	}

}
