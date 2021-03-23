package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;

import br.com.zupacademy.mercadolivre.models.Usuario;
import br.com.zupacademy.mercadolivre.validator.UniqueValue;

public class UsuarioFormDto {

	@NotBlank
	@Email
	@UniqueValue(campo = "email", classeDominio = Usuario.class)
	private String email;

	@NotNull
	@Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
	private String senha;

	public UsuarioFormDto(@NotBlank @Email String email,
			@NotNull @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String senha) {
		this.email = email;
		this.senha = senha;
	}

	public Usuario converter() {
		return new Usuario(email, senha = gerarSenhaHash(senha));
	}

	public String gerarSenhaHash(String senha) {
		String salt = BCrypt.gensalt();
		String senhaHash = BCrypt.hashpw(senha, salt);
		return senhaHash;
	}
}
