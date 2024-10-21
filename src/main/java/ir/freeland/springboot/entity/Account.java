package ir.freeland.springboot.entity;


import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "accountNumber", "iban"})
})

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;
    
    
    
    @NotNull(message = "account Number is required")
    @Size(min = 12 , max = 22 , message = "account Number is between 12-18 digits")
    @Pattern(regexp = "^[0-9]{22}$", message = "Account number must be 22 digits")
    @Column(nullable = false, unique = true)
    private String accountNumber;

    
    
    @NotNull(message = "accountBalance is required")
    @Min(value = 10000 , message = "minimum of account Balance is 10000 ")
	@Transient
	private BigDecimal accountBalance;
    
    
    
	@NotNull(message = "accountCreationDate is required")
    @Temporal(TemporalType.DATE)
	@Column(updatable = false)
	private Date accountCreationDate;
    
    
	
    @IBAN
    @NotNull(message = "IBAN is required")
    @Column(name = "shaba_Number", nullable = false, unique = false)
	private String iban;
    
    
    
    @ManyToOne(cascade = {
                          CascadeType.DETACH, CascadeType.MERGE,
                          CascadeType.PERSIST, CascadeType.REFRESH}
              )
    @JoinColumn(name="account_id")  
    private Person person; 
    
    
    
    private boolean isActive = false;
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    
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
    
    
    
    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
    
    
    
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    } 
    
    
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        person.addAccount(this);
    }
 


    @Override
    public String toString() {
        return "Person [id=" + id + ", accountNumber=" + accountNumber + ", accountBalance=" + accountBalance +
        		 ", accountCreationDate=" + accountCreationDate + ", iban=" + iban + "]";  
    }

    
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

   
}
