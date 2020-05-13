package fitwf.validation.annotation;

import fitwf.validation.UsernameValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidation.class)
public @interface ValidUsername {
    String message() default "Username should be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
