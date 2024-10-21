package ir.freeland.springboot.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.*;
import ir.freeland.springboot.entity.Gender;
import ir.freeland.springboot.entity.Person;

@Component
public class PersonValidator implements Validator{

	    @Override
	    public boolean supports(Class<?> clazz) {
	        return Person.class.equals(clazz);
	    }

	    @Override
	    public void validate(Object target, Errors errors) {
	    	Person person = (Person) target;

	        
	        //validation for gender
	        if (person.getGender() == null) {
	            errors.rejectValue("gender", "gender.empty", "Gender is required");
	        }
	       
	     // Validation for age
	        if (person.getBirthDate() == null) {
	            errors.rejectValue("birthDate", "birthDate.empty", "Birth date is required");
	        } else {
	            // get BirthDate
	            LocalDate birthDate = person.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            LocalDate currentDate = LocalDate.now();
	            int age = Period.between(birthDate, currentDate).getYears();

	            //  military status 
	            if (person.getGender() == Gender.MALE && age > 18) {
	                if (person.getMilitaryStatus() == null) {
	                    errors.rejectValue("militaryStatus", "militaryStatus.empty", 
	                    	              "Military status is required for males over 18 years old");
	                }
	            }
	        }
	      
	        
	  }
}
