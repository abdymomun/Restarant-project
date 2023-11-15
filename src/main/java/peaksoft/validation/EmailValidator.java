package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import peaksoft.exception.EmailBlankException;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isEmpty()) {
            return false;
        } else if (email.isBlank()) {
            throw new EmailBlankException("Email must not be empty");
        }
        if (!email.contains("@")) {
            throw new EmailBlankException("Email must @ ");
        }

        if (!email.endsWith(".com")) {
            throw new EmailBlankException("Email should be .com");
        }


        return true;
    }

}