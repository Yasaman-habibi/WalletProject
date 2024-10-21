package ir.freeland.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ir.freeland.springboot.entity.Account;
import ir.freeland.springboot.dto.AccountDto;
import ir.freeland.springboot.exception.ErrorResponseService;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	private final AccountService accountService;
	private final TransactionService transactionService;
	private final ErrorResponseService errorResponseService;
	

    @Autowired
    public AccountController(AccountService accountService,
    		                 ErrorResponseService errorResponseService, 
    		                 TransactionService transactionService) {              
                
    	this.accountService = accountService;
    	this.errorResponseService = errorResponseService;
    	this.transactionService = transactionService;
    }

    
    
    
    //active wallets related to the current user
    @GetMapping(value = "/myWallets" , produces = "application/json" )
    public List<Account> getActiveWalletsForCurrentUser(){
    	return accountService.getActiveWalletsForCurrentUser();
    }
    
    
    
    //person able to see the current balance.
    @GetMapping(value = "/balance/{accountNumber}" , produces = "application/json")
	public ResponseEntity<?> getBalance(@PathVariable("accountNumber") String accountNumber) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(accountService.getBalance(accountNumber));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
	}
 	
 	
 	
    //Creates a new wallet or account 
    @PostMapping(value = "/newWallet" , produces = "application/json")
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountDto accountDto, BindingResult result) {
        if (result.hasErrors()) {
        	
            logger.error("Validation errors: {}", result.getFieldErrors());
            return errorResponseService.returnValidationError(result);
        }
        
        Account createdAccount = accountService.create(accountDto);
        logger.info("Wallet created successfully with ID: {}", createdAccount.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
    


    //update wallet or account
    @PutMapping(value = "/updatewallet/{accountNumber}" , produces = "application/json")
    public ResponseEntity<Account> updateAccount(@PathVariable String accountNumber,
    		                                     @RequestBody @Valid AccountDto accountDto) {
    	
        Account updatedAccount = accountService.updateAccount(accountNumber, accountDto);
        return ResponseEntity.ok(updatedAccount);
    }



        //Users are able to add money. 
        @PostMapping(value = "/deposit/{accountNumber}" , produces = "application/json")
        public ResponseEntity<?> depositAmount(@PathVariable("accountNumber") String accountNumber,
        		                                @RequestParam BigDecimal amount) {
	         try {
		          return ResponseEntity.status(HttpStatus.OK)
		        		  .body(transactionService.depositAmount(accountNumber, amount));
	         } catch (Exception e) {
		          e.printStackTrace();
	              return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	         }
       }

        
        
       //Users are able to withdraw money. 
       @PostMapping(value = "/withdraw/{accountNumber}" , produces = "application/json")
       public ResponseEntity<?> withdrawAmount(@PathVariable("accountNumber") String accountNumber,
    		                                   @RequestParam BigDecimal amount) {
	      try {
		      return ResponseEntity.status(HttpStatus.OK)
		    		  .body(transactionService.withdrawAmount(accountNumber, amount));
	     } catch (Exception e) {
	      	e.printStackTrace();
		   return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	      }
       }
       
       
       
       @DeleteMapping("/deleteWallet/{accountNumber}")
   	public ResponseEntity<Object> deleteAccount(@PathVariable String accountNumber) {
   		return accountService.deleteAccount(accountNumber);
   	}
   
}


