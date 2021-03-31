package br.com.zupacademy.mercadolivre.utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import br.com.zupacademy.mercadolivre.models.Pergunta;

@Service
public class EnviaEmail {

	private Mailer mailer;

	public EnviaEmail(Mailer mailer) {
		this.mailer = mailer;
	}

	public void enviaEmailNovaPergunta(@NotNull @Valid Pergunta pergunta) {
		mailer.send("<html>"
						+ "<body>"
								+ "<strong>Email enviado.</strong>"												
						+ "</body>"
				   + "</html>",
				"Nova pergunta registrada.",
				pergunta.getUsuarioPergunta().getUsername(),
				"emailteste@mercadolivre.com",
				pergunta.getProdutoPergunta().getDonoDoProduto().getUsername());
	}
	
}