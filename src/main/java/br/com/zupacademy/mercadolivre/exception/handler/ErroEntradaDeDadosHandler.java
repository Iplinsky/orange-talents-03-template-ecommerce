package br.com.zupacademy.mercadolivre.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroEntradaDeDadosHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroEntradaDeDadosDto> handler(MethodArgumentNotValidException ex) {
		List<ErroEntradaDeDadosDto> lista = new ArrayList<>();

		List<FieldError> listFieldErros = ex.getBindingResult().getFieldErrors();
		listFieldErros.forEach(err -> {
			String mensagemDeErro = messageSource.getMessage(err, LocaleContextHolder.getLocale());
			ErroEntradaDeDadosDto eDto = new ErroEntradaDeDadosDto(err.getField(), mensagemDeErro);
			lista.add(eDto);
		});
		return lista;
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IllegalArgumentException.class)
	public String handler(IllegalArgumentException ex) {
		return ex.getMessage();
	}

}
