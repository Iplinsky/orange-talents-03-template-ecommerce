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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	private String emailLogin;

	@NotNull
	@Length(min = 6)
	private String senha;

	@NotNull
	@PastOrPresent
	private LocalDateTime momentoDoCadastro = LocalDateTime.now();

	public Usuario(@NotBlank @Email String emailLogin,
			@NotNull @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.") @Length(min = 6) String senha) {
		super();
		this.emailLogin = emailLogin;
		this.senha = senha;
	}

}
