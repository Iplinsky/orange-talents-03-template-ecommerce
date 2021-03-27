package br.com.zupacademy.mercadolivre.models.form.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.sun.istack.NotNull;

import br.com.zupacademy.mercadolivre.models.Categoria;
import br.com.zupacademy.mercadolivre.models.Produto;
import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.validator.CheckId;
import br.com.zupacademy.mercadolivre.validator.UniqueValue;

public class ProdutoFormDto {

	@NotBlank
	@UniqueValue(domain = Produto.class, field = "nome")
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

	@NotNull
	@CheckId(domain = Categoria.class, field = "id")
	@ManyToOne
	private Long idCategoria;

	@Size(min = 3)
	@Valid
	private List<CaracteristicaFormDto> caracteristicas = new ArrayList<CaracteristicaFormDto>();

	public ProdutoFormDto(@NotBlank String nome, @Positive BigDecimal valor,
			@NotNull @Min(0) Integer quantidadeDisponivel, @NotBlank @Length(max = 1000) String descricao,
			@NotNull Long idCategoria, @Size(min = 3) @Valid List<CaracteristicaFormDto> caracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.caracteristicas.addAll(caracteristicas);
	}

	public List<CaracteristicaFormDto> getCaracteristicas() {
		return caracteristicas;
	}

	public Produto converter(EntityManager em, Usuario donoDoProduto) {
		Assert.isTrue(idCategoria != null, "O produto deve pertencer a uma categoria!");
		Categoria categoria = em.find(Categoria.class, idCategoria);
		return new Produto(nome, valor, quantidadeDisponivel, descricao, categoria, donoDoProduto, caracteristicas);
	}

	public Set<String> recuperaCaracteristicasIguais() {
		HashSet<String> caracteristicasAux = new HashSet<String>();
		HashSet<String> caracteristicasIguais = new HashSet<String>();

		for (CaracteristicaFormDto caracteristica : caracteristicas) {
			if (!caracteristicasAux.add(caracteristica.getNome())) {
				caracteristicasIguais.add(caracteristica.getNome());
			}
		}
		return caracteristicasIguais;
	}

}
