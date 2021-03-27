package br.com.zupacademy.mercadolivre.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.sun.istack.NotNull;

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
	@NotNull
	@Valid
	private Categoria categoria;

	@ManyToOne
	@NotNull
	@Valid
	private Usuario donoDoProduto;

	@Size(min = 3)
	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	private Set<Caracteristica> caracteristicas = new HashSet<Caracteristica>();

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
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.descricao = descricao;
		this.categoria = categoria;
		this.donoDoProduto = donoDoProduto;
		this.caracteristicas.addAll(listaDeCaracteristicas.stream().map(caracteristica -> caracteristica.converte(this))
				.collect(Collectors.toSet()));

		Assert.isTrue(this.caracteristicas.size() >= 3, "Um produto precisa ter ao menos 3 características!");
	}

	public Long getId() {
		return this.id;
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

}
