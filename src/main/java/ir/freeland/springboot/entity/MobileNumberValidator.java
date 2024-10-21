package ir.freeland.springboot.persistence.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileNumberValidator implements 
ConstraintValidator<MobileNumber, String> {
	private static final String MOBILE_NUMBER_PATTERN = "^\\d{11}$";
	@Override
    public void initialize(MobileNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
        return mobileNumber != null && Pattern.matches(MOBILE_NUMBER_PATTERN, mobileNumber);
    }

}
