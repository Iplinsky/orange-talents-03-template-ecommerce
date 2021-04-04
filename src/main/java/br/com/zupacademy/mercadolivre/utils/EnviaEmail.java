package br.com.zupacademy.mercadolivre.utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import br.com.zupacademy.mercadolivre.enumerators.Gateway;
import br.com.zupacademy.mercadolivre.models.Compra;
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
	
	public void enviaEmailNovaCompra(@NotNull @Valid Compra compra) {
		mailer.send("<html>"
						+ "<body>"
								+ "<strong>Email enviado.</strong>"												
						+ "</body>"
				   + "</html>",
				"Uma nova requisição de compra foi realizada!",
				compra.getComprador().getUsername(),
				"emailteste@mercadolivre.com",
				compra.getProduto().getDonoDoProduto().getUsername());
		}
	
	public void enviaEmailConclusaoDeCompraComSucesso(@NotNull @Valid Compra compra ) {
		mailer.send("<html>"
				+ "<body>"
						+ "<strong>A sua compra referente ao produto " + compra.getProduto().getNome() +" foi concluída com sucesso!</strong>"
						+"<br>"
						+ "Detalhes da compra: "						
						+ compra.toString()
				+ "</body>"
		   + "</html>",
		"(Mercado Livre) - Compra conluída com sucesso!",
		compra.getComprador().getUsername(),
		"email.conslusao.de.compra@mercadolivre.com",
		compra.getComprador().getUsername());
	}
	
	public void enviaEmailFalhaNaConclusaoDeCompra(@NotNull @Valid Compra compra ) {
		mailer.send("<html>"
				+ "<body>"
						+ "<strong>Houve um problema durante o fechamento de sua compra."
						+"<br>"
						+ "Produto: " + compra.getProduto().getNome() + "</strong>"
						+ "<br>"
						+ "Você pode realizar o pagamento novamente através do link: "
							+ compra.getGateway() != null && compra.getGateway().equals(Gateway.PAGSEGURO) ?
									"http://localhost:8080/retorno-pagseguro/" + compra.getProduto().getId() 
										: "http://localhost:8080/retorno-paypal/" + compra.getProduto().getId()  						
				+ "</body>"
		   + "</html>",
		"(Mercado Livre) - Erro na conclusão de compra.",
		compra.getComprador().getUsername(),
		"email.conslusao.de.compra@mercadolivre.com",
		compra.getComprador().getUsername());
	}	
}