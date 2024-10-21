package ir.freeland.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.entity.PersonDetails;
import java.util.Optional;

public interface PersonDetailRepository extends CrudRepository<PersonDetails, Long> {
    Optional<PersonDetails> findByPerson_NationalId(String nationalId);
}
