package ir.freeland.springboot.entity;


import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    
    private BigDecimal amount;

    
    private boolean isWithdrawal;
    
    
	@NotNull
    @Temporal(TemporalType.DATE)
	@Column(updatable = false)
	private Date accountCreationDate;
    
   
    @ManyToOne(cascade = {
                          CascadeType.DETACH, CascadeType.MERGE,
                          CascadeType.PERSIST, CascadeType.REFRESH}
              )
    @JoinColumn(name="account_id" , nullable = false)  
    private Account account; 
    
    
    
    public Transaction() {}

    
    public Transaction(Date accountCreationDate, Account account,
    		           BigDecimal amount , boolean isWithdrawal) {
    	
        this.accountCreationDate = accountCreationDate;
        this.account = account;
        this.amount = amount;
        this.isWithdrawal = isWithdrawal;

    }
    
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
 
    
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    
    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
    
    
    
    public boolean getIsWithdrawal() {
        return isWithdrawal;
    }

    public void setIsWithdrawal(boolean isWithdrawal) {
        this.isWithdrawal = isWithdrawal;
    } 


}
