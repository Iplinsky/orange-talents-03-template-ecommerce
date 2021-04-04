package br.com.zupacademy.mercadolivre.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import br.com.zupacademy.mercadolivre.enumerators.StatusPagamento;

@Entity
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String idTransacao;

	@NotNull
	private StatusPagamento statusPagamento;

	@NotNull
	@PastOrPresent
	private LocalDateTime instanteDaTransacao;

	@ManyToOne
	@JoinColumn(name = "compra_id", nullable = false)
	@NotNull
	@Valid
	private Compra compra;

	@Deprecated
	public Transacao() {
	}

	public Transacao(@NotBlank String idTransacao, @NotNull StatusPagamento statusPagamento,
			@NotNull @Valid Compra compra) {
		this.idTransacao = idTransacao;
		this.statusPagamento = statusPagamento;
		this.instanteDaTransacao = LocalDateTime.now();
		this.compra = compra;
	}

	public String getIdTransacao() {
		return this.idTransacao;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTransacao == null) ? 0 : idTransacao.hashCode());
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
		Transacao other = (Transacao) obj;
		if (idTransacao == null) {
			if (other.idTransacao != null)
				return false;
		} else if (!idTransacao.equals(other.idTransacao))
			return false;
		return true;
	}

}
