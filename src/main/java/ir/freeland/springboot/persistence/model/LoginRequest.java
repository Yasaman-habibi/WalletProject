package ir.freeland.springboot.persistence.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginRequest {
	
	 @NotNull
	 @Pattern(regexp = "^\\d{10}$", message = "National id contains 10 number")
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
