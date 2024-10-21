package ir.freeland.springboot.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;

public class AccountDto {
	
	@NotNull
	@Size(min = 12 , max = 22)
    @Pattern(regexp = "^[0-9]{22}$")
    @Column(nullable = false, unique = true)
    private String accountNumber;

	
	
    @NotNull
    @Min(value = 15000)
    private BigDecimal accountBalance;



   public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    
    
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}

