package ir.freeland.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.entity.Transaction;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    
    List<Transaction> findByAccount_Id(long accountId);
	
	List<Transaction> findByPerson_NationalId(String nationalId);
}
