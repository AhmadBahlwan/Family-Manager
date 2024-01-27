package bahlawan.alwafidin.personalInfo.repositories;

import bahlawan.alwafidin.personalInfo.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Page<Person> findAll(Pageable pageable);

    @Query("SELECT p FROM Person p WHERE " +
            "CONCAT (p.firstName, ' ', p.middleName, ' ', p.lastName) LIKE %?1% " +
            "OR CONCAT (p.firstName, ' ', p.lastName) LIKE %?1% " +
            "OR p.nationalNumber LIKE %?1% " +
            "OR p.phoneNumber LIKE %?1%" )
    Page<Person> findAll(String keyword, Pageable pageable);

    Person findByNationalNumber(String nationalNumber);

    Long countById(Integer id);
}
