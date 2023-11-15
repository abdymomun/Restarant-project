package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.exception.NotFoundException;

public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence> {
    @Override
    public void initialize(NotBlank constraintAnnotation) {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
if (value.length() == 0){
    throw new NotFoundException("Not must blank");
}
        return value != null && value.toString().trim().length() > 0;
    }
}
