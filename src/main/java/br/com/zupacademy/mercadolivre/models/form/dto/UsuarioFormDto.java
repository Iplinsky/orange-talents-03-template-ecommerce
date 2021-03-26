package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.validator.UniqueValue;

public class UsuarioFormDto {

	@NotBlank
	@Email
	@UniqueValue(field = "email", domain = Usuario.class)
	private String email;

	@NotBlank
	@Length(min = 6)
	private String senha;

	public UsuarioFormDto(@NotBlank @Email String email,
			@NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String senha) {
		this.email = email;
		this.senha = senha;
	}

	// Já encaminha a senha criptografada para ser salva no banco de dados
	public Usuario converter() {
		return new Usuario(email, new BCryptPasswordEncoder().encode(senha));
	}
}
