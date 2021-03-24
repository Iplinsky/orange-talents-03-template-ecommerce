package br.com.zupacademy.mercadolivre.validator;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.Assert;

public class UniqueValueConstraint implements ConstraintValidator<UniqueValue, Object> {

	private String campo;
	private Class<?> classeDominio;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void initialize(UniqueValue constraint) {
		this.campo = constraint.field();
		this.classeDominio = constraint.domain();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query = em
				.createQuery(String.format("SELECT 1 FROM %s WHERE %s = :value", classeDominio.getName(), campo))
				.setParameter("value", value);

		List<?> list = query.getResultList();

		Assert.state(list.size() <= 1,
				String.format("Já existe um %s com esse %s cadastrado!", classeDominio.getName(), campo));

		return list.isEmpty();
	}

}
