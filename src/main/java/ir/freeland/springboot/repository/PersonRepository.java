package ir.freeland.springboot.persistence.repo;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.persistence.model.Person;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByNationalId(String nationalId);
}
