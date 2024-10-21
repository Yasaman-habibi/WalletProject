package ir.freeland.springboot.persistence.model;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IBANValidator implements ConstraintValidator<IBAN, String> {
	private static final String IBAN_PATTERN = "^IR[0-9]{24}$";
	
	@Override
    public void initialize(IBAN constraintAnnotation) {
    }

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext context) {
        return iban != null && Pattern.matches(IBAN_PATTERN, iban); 
        		
    }

}
