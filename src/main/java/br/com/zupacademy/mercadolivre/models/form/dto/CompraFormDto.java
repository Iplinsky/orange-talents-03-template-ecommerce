package br.com.zupacademy.mercadolivre.models.form.dto;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zupacademy.mercadolivre.enumerators.Gateway;
import br.com.zupacademy.mercadolivre.enumerators.StatusCompra;
import br.com.zupacademy.mercadolivre.models.Compra;
import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.validator.CheckId;

public class CompraFormDto {

	@Positive
	@NotNull
	private Integer quantidadeDeItens;

	@CheckId(domain = Produto.class, field = "id")
	@NotNull
	private Long codProduto;

	@NotNull
	private BigDecimal valorAtualDoProduto;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gateway gateway;

	public CompraFormDto(@Positive @NotNull Integer quantidadeDeItens, @NotNull Long codProduto,
			@NotNull BigDecimal valorAtualDoProduto, @NotNull Gateway gateway) {
		this.quantidadeDeItens = quantidadeDeItens;
		this.codProduto = codProduto;
		this.valorAtualDoProduto = valorAtualDoProduto;
		this.gateway = gateway;
	}

	public Compra converter(Usuario usuarioComprador, EntityManager em) {
		Produto produto = em.find(Produto.class, codProduto);
		produto.abateEstoqueDoProduto(quantidadeDeItens);
		return new Compra(this.quantidadeDeItens, produto, usuarioComprador, this.valorAtualDoProduto, this.gateway,
				StatusCompra.INICIADA);
	}

	public Integer getQuantidadeDeItens() {
		return quantidadeDeItens;
	}

	public Long getCodProduto() {
		return codProduto;
	}

	public Gateway getGateway() {
		return gateway;
	}

}
