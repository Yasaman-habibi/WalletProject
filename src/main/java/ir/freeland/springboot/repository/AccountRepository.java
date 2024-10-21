package ir.freeland.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.entity.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
	
	Optional<Account> findByAccountNumber(String accountNumber);
	
    Optional<Account> findByAccount_NationalId(String nationalId);

    List<Account> findByAccount_NationalIdAndIsActive(String nationalId, boolean isActive);
}
