package br.com.zupacademy.mercadolivre.utils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface Mailer {

	/*
	 * body 	-> Corpo do email
	 * subject 	-> Assunto do email
	 * nameFrom -> Nome que será associado ao provedor de email
	 * from 	-> Origem do email
	 * to 		-> Email de destino
	 */
	
	void send(@NotBlank String body, @NotBlank String subject, @NotBlank String nameFrom, @NotBlank @Email String from, @NotBlank @Email String to);
	
}
