package com.project.citizens.repository;

import java.util.List;

import com.project.citizens.domain.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByFirstNameContainingIgnoringCase(String firstname);
    List<Person> findByLastNameContainingIgnoringCase(String lastname);    
    List<Person> findByMiddleNameContainingIgnoringCase(String middlename);

    @Query("select p from Person p where upper(p.firstName) like concat('%', upper(?1), '%') and upper(p.lastName) like concat('%', upper(?2), '%') and upper(p.middleName) like concat('%', upper(?3), '%')")
    List<Person> findByFIO(String firstName, String lastName, String middleName);

    List<Person> findByFirstNameContainingAndLastNameContainingAllIgnoringCase(String firstname, String lastname);
    List<Person> findByFirstNameContainingAndMiddleNameContainingAllIgnoringCase(String firstname, String middlename);
    List<Person> findByLastNameContainingAndMiddleNameContainingAllIgnoringCase(String lastname, String middlename);

    @Query("select distinct p from Person p, Doc d where d.person.id = p.id and upper(d.docNumber) like concat('%', upper(?1), '%')")
    List<Person> findByDocNumber(String docNumber);

    @Query("select p from Person p where DAY(p.birthDate) = ?1 and MONTH(p.birthDate) = ?2 and YEAR(p.birthDate) = ?3")
    List<Person> findByBirthDate(Integer birthDay, Integer birthMonth, Integer birthYear);
}