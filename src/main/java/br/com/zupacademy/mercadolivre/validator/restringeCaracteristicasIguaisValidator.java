package br.com.zupacademy.mercadolivre.validator;

import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zupacademy.mercadolivre.models.form.dto.ProdutoFormDto;

public class restringeCaracteristicasIguaisValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProdutoFormDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}

		ProdutoFormDto form = (ProdutoFormDto) target;

		Set<String> nomesDeCaracteristicasIguais = form.recuperaCaracteristicasIguais();

		if (!nomesDeCaracteristicasIguais.isEmpty()) {
			errors.rejectValue("caracteristicas", null,
					"Não é possível incluir características iguais! " + nomesDeCaracteristicasIguais);
		}

	}

}
