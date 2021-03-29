package br.com.zupacademy.mercadolivre.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Opiniao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Min(value = 1)
	@Max(value = 5)
	private Integer nota;

	@NotBlank
	private String titulo;

	@NotBlank
	@Length(max = 500)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "usuario_opiniao_id")
	@NotNull
	@Valid
	private Usuario usuarioOpiniao;

	@ManyToOne
	@JoinColumn(name = "produto_opinado_id")
	@NotNull
	@Valid
	private Produto produtoOpinado;

	@Deprecated
	public Opiniao() {
	}

	public Opiniao(@Min(value = 1) @Max(value = 5) Integer nota, @NotBlank String titulo,
			@NotBlank @Length(max = 500) String descricao, @NotNull @Valid Usuario usuarioOpiniao,
			@NotNull @Valid Produto produtoOpinado) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
		this.usuarioOpiniao = usuarioOpiniao;
		this.produtoOpinado = produtoOpinado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nota == null) ? 0 : nota.hashCode());
		result = prime * result + ((produtoOpinado == null) ? 0 : produtoOpinado.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((usuarioOpiniao == null) ? 0 : usuarioOpiniao.hashCode());
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
		Opiniao other = (Opiniao) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (nota == null) {
			if (other.nota != null)
				return false;
		} else if (!nota.equals(other.nota))
			return false;
		if (produtoOpinado == null) {
			if (other.produtoOpinado != null)
				return false;
		} else if (!produtoOpinado.equals(other.produtoOpinado))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (usuarioOpiniao == null) {
			if (other.usuarioOpiniao != null)
				return false;
		} else if (!usuarioOpiniao.equals(other.usuarioOpiniao))
			return false;
		return true;
	}

}
