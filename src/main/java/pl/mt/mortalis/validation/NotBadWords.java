package pl.mt.mortalis.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NotBadWordsValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface NotBadWords {
    String message() default "Text contains bad words";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
