package com.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = LllegalCharVaildtorCheck.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface LllegalCharVaildtor {

	String message() default "Content contains illegal characters";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
}
