package ir.freeland.springboot.persistence.repo;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.persistence.model.PersonDetails;
import java.util.Optional;

public interface PersonDetailRepository extends CrudRepository<PersonDetails, Long> {
    Optional<PersonDetails> findByPerson_NationalId(String nationalId);
}
