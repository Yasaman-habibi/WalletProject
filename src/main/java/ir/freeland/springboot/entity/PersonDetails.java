package ir.freeland.springboot.persistence.model;

import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


	
    @Entity
      public class PersonDetails implements UserDetails {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;


	    @NotNull
	    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    @JoinColumn(name = "person_id", referencedColumnName = "id")
	    private Person person;

	 
	    
	    public PersonDetails() {}

	    public PersonDetails(Person person) {
	        this.person = person;
	    }



       @Override
	    public boolean isAccountNonExpired() {
	        return person.isActive();
	    }

       
       
	    @Override
	    public boolean isAccountNonLocked() {
	        return person.isActive();
	    }

	    
	    
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return person.isActive();
	    }

	    
	    
	    @Override
	    public boolean isEnabled() {
	        return person.isActive();
	    }

	    
	    
	    @Override
	    public String getUsername() {
	        return person.getNationalId();
	    }
 
	    
	    
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of();
	    }

	    
	    @Override
	    public String getPassword() {
	        return person.getPassword();
	    }
        
	    
	    

	   
	    
	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    
	    
	    public Person getPerson() {
	        return person;
	    }

	    public void setPerson(Person person) {
	        this.person = person;
	    }

	    
	    
	    @Override
	    public String toString() {
	        return "PersonDetail{" + "id=" + id + ", person=" + person.toString() + '}';
	    }
	}


