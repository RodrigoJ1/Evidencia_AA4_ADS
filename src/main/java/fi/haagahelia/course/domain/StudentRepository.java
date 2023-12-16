package fi.haagahelia.course.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Citas, Long> {

    List<Citas> findByLastName(String lastname);
    
}
