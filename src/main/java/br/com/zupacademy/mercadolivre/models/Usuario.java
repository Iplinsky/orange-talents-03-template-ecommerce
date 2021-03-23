package br.com.zupacademy.mercadolivre.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	private String email;

	@NotNull
	@Length(min = 6)
	private String senha;

	@NotNull
	@PastOrPresent
	private LocalDateTime momentoDoCadastro = LocalDateTime.now();

	public Usuario(@NotBlank @Email String email, @NotNull @Length(min = 6) String senha) {
		this.email = email;
		this.senha = senha;
	}

}
