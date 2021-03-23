package br.com.zupacademy.mercadolivre.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValueConstraint.class)
public @interface UniqueValue {

	String message() default "O valor informado já foi cadastrado!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String campo() default "";

	Class<?> classeDominio();

}
