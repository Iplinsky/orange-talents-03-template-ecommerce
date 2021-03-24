/**
 * 
 */
package br.com.zupacademy.mercadolivre.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CheckIdConstraint.class)
public @interface CheckId {

	String message() default "Registro não identificado!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String field() default "";

	Class<?> domain();
}
