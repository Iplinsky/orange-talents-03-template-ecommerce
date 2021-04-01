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
			@NotNull @Valid Usuario comprador, @NotNull BigDecimal valorAtualDoProduto, @NotNull Gateway gateway,
			@NotNull StatusCompra statusCompra) {
		this.quantidadeDeItens = quantidadeDeItens;
		this.produto = produto;
		this.comprador = comprador;
		this.valorAtualDoProduto = valorAtualDoProduto;
		this.gateway = gateway;
		this.statusCompra = statusCompra;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comprador == null) ? 0 : comprador.hashCode());
		result = prime * result + ((gateway == null) ? 0 : gateway.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		if (comprador == null) {
			if (other.comprador != null)
				return false;
		} else if (!comprador.equals(other.comprador))
			return false;
		if (gateway != other.gateway)
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
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
