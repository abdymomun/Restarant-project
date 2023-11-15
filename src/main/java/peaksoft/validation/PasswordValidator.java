package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.exception.PasswordException;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    if (password.isBlank()){
    throw new PasswordException("Password not must blank !");
    } else if (password.length() < 7) {
        throw new PasswordException("Password length must 7  ");
    }
        return password != null && password.length()>=7;
    }
}
