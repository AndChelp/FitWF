package fitwf.validation.annotation;

import fitwf.validation.PasswordValidation;
import fitwf.validation.UsernameValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidation.class)
public @interface ValidPassword {
    String message() default "Password should be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
