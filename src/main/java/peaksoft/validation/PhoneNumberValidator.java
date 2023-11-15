package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.exception.PhoneNumberException;


public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid,String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {

            if (phoneNumber.length() > 13){
                throw new PhoneNumberException("Length number must be 13 digits");
            }
            else if (!phoneNumber.startsWith("+996")) {
                throw new PhoneNumberException("Should begin with +996");
            }

        return phoneNumber != null && phoneNumber.startsWith("+996") && phoneNumber.length() == 13;

    }

}
