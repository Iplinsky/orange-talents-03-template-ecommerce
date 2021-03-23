package br.com.zupacademy.mercadolivre.models.form.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;

import br.com.zupacademy.mercadolivre.models.Usuario;

public class UsuarioFormDto {

	@NotBlank
	@Email
	private String emailLogin;

	@NotNull
	@Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
	private String senha;

	public UsuarioFormDto(@NotBlank @Email String emailLogin,
			@NotNull @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String senha) {
		this.emailLogin = emailLogin;
		this.senha = senha;
	}

	public Usuario converter() {
		return new Usuario(emailLogin, senha = gerarSenhaHash(senha));
	}

	public String gerarSenhaHash(String senha) {
		String salt = BCrypt.gensalt();
		String senhaHash = BCrypt.hashpw(senha, salt);
		return senhaHash;
	}
}
