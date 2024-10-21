package ir.freeland.springboot.web;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ir.freeland.springboot.persistence.model.Person;
import ir.freeland.springboot.persistence.repo.PersonRepository;
import ir.freeland.springboot.web.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	private final PersonRepository personRepository; 
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
    @Autowired
    public PersonService(PersonRepository personRepository , 
    		             PasswordEncoder passwordEncoder,
    		             AuthenticationManager authenticationManager) {
    	this.personRepository = personRepository;
    	this.passwordEncoder = passwordEncoder;
    	this.authenticationManager = authenticationManager;
    }

    
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    
    
    public Person findByNationalId(String id) {
    	Person person =
    			personRepository.findByNationalId(id).orElseThrow(
                        () -> new ResourceNotFoundException("This person does not exists")
                );
        if(!person.isActive())
            throw new ResourceNotFoundException("person not found");

        return person;
    }
    
    
    
    public String login(String nationalId, String password) {
        try {
     
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(nationalId, password));
            
            return "Login successful for user: " + nationalId;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid nationalId/password supplied");
        }
    }
    
    
    
    public void changePassword(String nationalId, ChangePasswordDto changePasswordDto) {
    	Person person = personRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new ResourceNotFoundException("person not found"));

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), person.getPassword())) {
            throw new BadCredentialsException("OldPassword is incorrect");
        }

        person.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        personRepository.save(person);
    }
    
    
    
    public boolean deactivatePerson(String nationalId) {
        Optional<Person> personOptional = personRepository.findByNationalId(nationalId);
        if (personOptional.isPresent()) {
        	Person person = personOptional.get();
        	person.setActive(false); 
            personRepository.save(person);
            return true;
        }
        return false;
    }
    
    
    
    public void savePerson(Person person) {
    	personRepository.save(person);
    }
    
}

