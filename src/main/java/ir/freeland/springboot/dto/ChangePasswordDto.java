package ir.freeland.springboot.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class ChangePasswordDto {
	
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
		    message = "Password must be at least 8 characters long and contain at least one uppercase letter,"
		    		+ " one lowercase letter, and one number")
    private String oldPassword;

	
	
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
           message = "Password must be at least 8 characters long and contain at least one uppercase letter,"
    		+ " one lowercase letter, and one number")
    private String newPassword;

    
    
    public ChangePasswordDto() {}
    
    
    	public ChangePasswordDto(String oldPassword , String newPassword) {
    		this.oldPassword = oldPassword;
    		this.newPassword = newPassword;
    }
    
    	
    	
    	
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}


