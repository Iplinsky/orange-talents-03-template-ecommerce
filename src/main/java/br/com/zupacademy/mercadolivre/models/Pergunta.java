package br.com.zupacademy.mercadolivre.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
public class Pergunta implements Comparable<Pergunta> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String titulo;

	@PastOrPresent
	private LocalDateTime instanteCriacao = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "usuario_pergunta_id", nullable = false)
	@NotNull
	private Usuario usuarioPergunta;

	@ManyToOne
	@JoinColumn(name = "produto_pergunta_id", nullable = false)
	@NotNull
	private Produto produtoPergunta;

	@Deprecated
	public Pergunta() {
	}

	public Pergunta(@NotBlank String titulo, @NotNull Usuario usuarioPergunta, @NotNull Produto produtoPergunta) {
		this.titulo = titulo;
		this.usuarioPergunta = usuarioPergunta;
		this.produtoPergunta = produtoPergunta;
	}

	public Usuario getUsuarioPergunta() {
		return this.usuarioPergunta;
	}

	public Produto getProdutoPergunta() {
		return this.produtoPergunta;
	}

	public String getTitulo() {
		return this.titulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produtoPergunta == null) ? 0 : produtoPergunta.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((usuarioPergunta == null) ? 0 : usuarioPergunta.hashCode());
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
		Pergunta other = (Pergunta) obj;
		if (produtoPergunta == null) {
			if (other.produtoPergunta != null)
				return false;
		} else if (!produtoPergunta.equals(other.produtoPergunta))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (usuarioPergunta == null) {
			if (other.usuarioPergunta != null)
				return false;
		} else if (!usuarioPergunta.equals(other.usuarioPergunta))
			return false;
		return true;
	}

	@Override
	public int compareTo(Pergunta o) {
		return this.titulo.compareTo(o.titulo);
	}

}
