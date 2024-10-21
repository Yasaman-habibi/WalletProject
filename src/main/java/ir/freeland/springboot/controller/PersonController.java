package ir.freeland.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ir.freeland.springboot.persistence.model.LoginRequest;
import ir.freeland.springboot.persistence.model.Person;
import ir.freeland.springboot.persistence.model.JwtTokenProvider;

import java.util.stream.Collectors;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/persons")
public class PersonController {

    PersonService personService;
    PersonValidator personValidator;
    AuthenticationManager authenticationManager;
    JwtTokenProvider tokenProvider;

    
    @Autowired
    public PersonController(PersonService personService,
    		                PersonValidator personValidator,
    		                AuthenticationManager authenticationManager,
    		                JwtTokenProvider tokenProvider) {
                            
       
    	this.personService = personService;
        this.personValidator = personValidator;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


  
    
   //Retrieving current user account information
    @GetMapping(value = "/myInformation" , produces = "application/json")
    		        
    public ResponseEntity<Person> getAccountDetails() {
 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nationalId = authentication.getName();

        Person person = personService.findByNationalId(nationalId);

        return ResponseEntity.ok(person);
    }
    
    
    
   //Change password
    @PutMapping(value = "/mypassword" , produces = "application/json")
    
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
      
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nationalId = authentication.getName();
        
        personService.changePassword(nationalId, changePasswordDto);

        return ResponseEntity.ok().build();
    }
    
    
    
    //delete account of person
    @DeleteMapping(value = "/deleteAccount/{nationalId}"  , produces = "application/json")
    public ResponseEntity<String> deactivatePerson(@PathVariable String nationalId) {
        boolean isDeactivated = personService.deactivatePerson(nationalId);
        if (isDeactivated) {
            return ResponseEntity.ok(" account of person deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("person not found.");
        }
    }

    
    
    //create account for person
    @PostMapping(value = "/registerAccount"  , produces = "application/json")
    public ResponseEntity<String> registerPerson(@Valid @RequestBody Person person, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));
        }
        personService.savePerson(person);
        return ResponseEntity.ok("person registered successfully");
    }
    
    
    
    //login user/person
    @PostMapping(value = "/loginAccount"  , produces = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response = personService.login(loginDto.getNationalId(), loginDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
