package ir.freeland.springboot.persistence.repo;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.persistence.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
	
	Optional<Account> findByAccountNumber(String accountNumber);
	
    Optional<Account> findByAccount_NationalId(String nationalId);

    List<Account> findByAccount_NationalIdAndIsActive(String nationalId, boolean isActive);
}
