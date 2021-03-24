package br.com.zupacademy.mercadolivre.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIdConstraint implements ConstraintValidator<CheckId, Long> {

	private String field;
	private Class<?> domain;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void initialize(CheckId checkIdConstraint) {
		this.field = checkIdConstraint.field();
		this.domain = checkIdConstraint.domain();
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		Query query = em.createQuery(String.format("SELECT 1 FROM %s WHERE %s = :value", domain.getName(), field))
				.setParameter("value", value);

		return !query.getResultList().isEmpty();
	}

}
