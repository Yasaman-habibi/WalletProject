package ir.freeland.springboot.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import jakarta.persistence.Column;

public class LoginDto {
	
	    @NotNull
	    @Pattern(regexp = "^\\d{10}$", message = "National id contains 10 number")
	    @Column(name = "email" , unique = true ,nullable = false)
	    private String nationalId; 
	    
	
	    @NotNull
	    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
	           message = "Password must be at least 8 characters long and contain at least one uppercase letter,"
	    		+ " one lowercase letter, and one number")
	    private String password;
  

	    
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

