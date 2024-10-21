package ir.freeland.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ir.freeland.springboot.persistence.model.Account;
import ir.freeland.springboot.persistence.model.AccountDto;
import ir.freeland.springboot.persistence.repo.AccountRepository;
import ir.freeland.springboot.web.exception.ResourceNotFoundException;
import ir.freeland.springboot.web.exception.ValidationException;
import org.springframework.security.core.Authentication;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

	
	private final AccountRepository accountRepository;
	
	
    @Autowired
    public AccountService(AccountRepository accountRepository) {
    	this.accountRepository = accountRepository;
    }

    

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    
    
    public List<Account> getActiveWalletsForCurrentUser() {
        String currentUserNationalId = SecurityContextHolder
        		                      .getContext()
        		                      .getAuthentication()
        		                      .getName();
        return accountRepository.findByAccount_NationalId(currentUserNationalId)
                .stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
    }

    
    
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    
    
    public Account create(AccountDto accountDto) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentUsername = authentication.getName();
    	
    	Account account = new Account();
    	account.setAccountNumber(accountDto.getAccountNumber());
    	account.setAccountBalance(accountDto.getAccountBalance());
    	account.setActive(true); 
    	
    	 try {
             account = accountRepository.save(account);
         } catch (Exception ex) {
             throw new ValidationException("Something went wrong!");
         }

         return account;
     }

      
    
    public BigDecimal getBalance(String accountNumber) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            return account.getAccountBalance();
        } else {
            throw new RuntimeException("Account not found for this account Number: " + accountNumber);
        } 
    }
    
    
    
    public Account updateAccount(String accountNumber, AccountDto accountDto) {
    	
    	Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
    	
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setAccountBalance(accountDto.getAccountBalance());
            account.setAccountNumber(accountDto.getAccountNumber());

            return accountRepository.save(account);
        } else {
            throw new ResourceNotFoundException("Account not found");
        }
    }
    
    
    
    public ResponseEntity<Object> deleteAccount(String accountNumber) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
      
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            accountRepository.delete(account);
          
            return ResponseEntity.status(HttpStatus.OK).body("wallet deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("wallet not found.");
        }
    }
}
