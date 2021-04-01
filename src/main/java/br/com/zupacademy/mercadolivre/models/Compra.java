package br.com.zupacademy.mercadolivre.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zupacademy.mercadolivre.enumerators.Gateway;
import br.com.zupacademy.mercadolivre.enumerators.StatusCompra;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getComprador() == null) ? 0 : getComprador().hashCode());
		result = prime * result + ((gateway == null) ? 0 : gateway.hashCode());
		result = prime * result + ((getProduto() == null) ? 0 : getProduto().hashCode());
		result = prime * result + ((quantidadeDeItens == null) ? 0 : quantidadeDeItens.hashCode());
		result = prime * result + ((statusCompra == null) ? 0 : statusCompra.hashCode());
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
		if (gateway != other.gateway)
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
		if (statusCompra != other.statusCompra)
			return false;
		if (valorAtualDoProduto == null) {
			if (other.valorAtualDoProduto != null)
				return false;
		} else if (!valorAtualDoProduto.equals(other.valorAtualDoProduto))
			return false;
		return true;
	}

}
