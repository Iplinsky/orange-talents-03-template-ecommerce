package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Length(min = 6)
	private String senha;

	public LoginForm(@NotBlank @Email String email, @NotBlank @Length(min = 6) String senha) {
		this.email = email;
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}

}
