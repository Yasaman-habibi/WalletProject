package ir.freeland.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import ir.freeland.springboot.entity.Person;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByNationalId(String nationalId);
}
