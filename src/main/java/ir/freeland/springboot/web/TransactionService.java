package ir.freeland.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ir.freeland.springboot.persistence.model.Account;
import ir.freeland.springboot.persistence.model.Transaction;
import ir.freeland.springboot.persistence.repo.AccountRepository;
import ir.freeland.springboot.persistence.repo.TransactionRepository;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {

	
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	
    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                               AccountRepository accountRepository) {
    	this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

   
    public TransactionResponse withdrawAmount(String accountNumber, BigDecimal amount) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountOpt.isPresent()) {
        	Account account = accountOpt.get();
            if (account.getAccountBalance().compareTo(amount) >= 0) {
            	account.setAccountBalance(account.getAccountBalance().subtract(amount));
                accountRepository.save(account);

                Transaction transaction = new Transaction();
                transaction.setAccount(account);
                transaction.setAmount(amount);
                transaction.setIsWithdrawal(true);
                transactionRepository.save(transaction);

                return new TransactionResponse("SUCCESS", "Amount withdrawn successfully.");
            } else {
                return new TransactionResponse("FAILURE", "Insufficient balance.");
            }
        } else {
            return new TransactionResponse("FAILURE", "Wallet not found.");
        }
    } 
        
        
        public TransactionResponse depositAmount(String accountNumber, BigDecimal amount) {
            Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
            if (accountOpt.isPresent()) {
            	Account account = accountOpt.get();
                	account.setAccountBalance(account.getAccountBalance().add(amount));
                    accountRepository.save(account);

                    Transaction transaction = new Transaction();
                    transaction.setAccount(account);
                    transaction.setAmount(amount);
                    transaction.setIsWithdrawal(false);
                    transactionRepository.save(transaction);

                    return new TransactionResponse("SUCCESS", "Amount deposited successfully.");
            } else {
                return new TransactionResponse("FAILURE", "Account not found.");
            }
    }
}

    
   

