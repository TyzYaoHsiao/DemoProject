package com.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * refer: [5. Validating That a String Matches a Value of an Enum ](https://www.baeldung.com/javax-validations-enums)
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NameOfEnumValidator.class)
public @interface NameOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of enum {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
