package fitwf.validation;

import fitwf.validation.annotation.ValidPassword;
import fitwf.validation.annotation.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidation implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[\\w\\d-._]{3,60}$";
        return Pattern.compile(regex).matcher(s).matches();
    }
}
