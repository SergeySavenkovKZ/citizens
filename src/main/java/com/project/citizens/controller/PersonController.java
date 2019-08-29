package com.project.citizens.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.project.citizens.repository.PersonRepository;
import com.project.citizens.domain.Person;
import com.project.citizens.dto.PersonDto;
import com.project.citizens.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Persons Management System", description="API относящиеся к гражданам")
@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @ApiOperation(value = "Получение списка граждан")
    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() throws ResourceNotFoundException {
        return getListDto(personRepository.findAll());
    }

    @ApiOperation(value = "Пролучить данные гражданина по его ID")
    @GetMapping("/persons/{personId}")
    public ResponseEntity<PersonDto> getPersonById(
        @ApiParam(value = "Идентификатор, по которому будут получены данные по гражданину", required = true) 
        @PathVariable(value = "personId") Long personId) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Данные не найдены по ID :: " + personId));
        return ResponseEntity.ok().body(new PersonDto(person));
    }

    @ApiOperation(value = "Пролучить список граждан согласно ФИО")
    @GetMapping("/persons/search/fio")
    public List<PersonDto> getPersonsByFIO(
        @ApiParam(value = "Имя", required = false)
        @RequestParam(value = "firstname", required = false, defaultValue = "") String firstName,
        @ApiParam(value = "Фамилия", required = false)
        @RequestParam(value = "lastname", required = false, defaultValue = "") String lastName,
        @ApiParam(value = "Отчество", required = false)
        @RequestParam(value = "middlename", required = false, defaultValue = "") String middleName) throws ResourceNotFoundException {
        
        if (!firstName.isEmpty() && !lastName.isEmpty() && middleName.isEmpty()) {
            return getListDto(personRepository.findByFirstNameContainingAndLastNameContainingAllIgnoringCase(firstName, lastName));
        } else if (!firstName.isEmpty() && lastName.isEmpty() && !middleName.isEmpty()) {
            return getListDto(personRepository.findByFirstNameContainingAndMiddleNameContainingAllIgnoringCase(firstName, middleName));
        } else if (firstName.isEmpty() && !lastName.isEmpty() && !middleName.isEmpty()) {
            return getListDto(personRepository.findByLastNameContainingAndMiddleNameContainingAllIgnoringCase(lastName, middleName));
        } else if (!firstName.isEmpty() && lastName.isEmpty() && middleName.isEmpty()) {
            return getListDto(personRepository.findByFirstNameContainingIgnoringCase(firstName));            
        } else if (firstName.isEmpty() && !lastName.isEmpty() && middleName.isEmpty()) {
            return getListDto(personRepository.findByLastNameContainingIgnoringCase(lastName));
        } else if (firstName.isEmpty() && lastName.isEmpty() && !middleName.isEmpty()) {
            return getListDto(personRepository.findByMiddleNameContainingIgnoringCase(middleName));
        }

        return getListDto(personRepository.findByFIO(firstName, lastName, middleName));
    }

    @ApiOperation(value = "Пролучить список граждан согласно номеру документа")
    @GetMapping("/persons/search/docNumber")
    public List<PersonDto> getPersonsByDocNumber(
        @ApiParam(value = "Номер документа", required = true)
        @RequestParam(value = "docnumber") String docNumber) throws ResourceNotFoundException {
        
        return getListDto(personRepository.findByDocNumber(docNumber));
    }

    @ApiOperation(value = "Пролучить список граждан согласно дате рождения")
    @GetMapping("/persons/search/birthDate")
    public List<PersonDto> getPersonsByBirthDate(
        @ApiParam(value = "День", required = true)
        @RequestParam(value = "birthday") Integer birthDay,
        @ApiParam(value = "Месяц", required = true)
        @RequestParam(value = "birthmonth") Integer birthMonth,
        @ApiParam(value = "Год", required = true)
        @RequestParam(value = "birthyear") Integer birthYear) throws ResourceNotFoundException {
        
        return getListDto(personRepository.findByBirthDate(birthDay, birthMonth, birthYear));
    }

    @ApiOperation(value = "Добавить информацию о гражданине")
    @PostMapping("/persons")
    public ResponseEntity<PersonDto> createPerson(
            @ApiParam(value = "Данные по гражданину", required = true) 
            @Valid @RequestBody PersonDto person) {
        
        person.setId(null);        
        return save(person.toEntity(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Обновить данные по гражданину согласно его ID")
    @PutMapping("/persons/{personId}")
    public ResponseEntity<PersonDto> updatePost (
        @ApiParam(value = "Идентификатор гражданина", required = true)
        @PathVariable Long personId,
        @ApiParam(value = "Данные по гражданину", required = true) 
        @Valid @RequestBody PersonDto personRequest) throws ResourceNotFoundException {
        
        Person updPerson = personRepository.findById(personId).map(person -> {
            person.setSex(personRequest.getSex());
            person.setFirstName(personRequest.getFirstName());
            person.setLastName(personRequest.getLastName());
            person.setMiddleName(personRequest.getMiddleName());
            person.setLocation(personRequest.getLocation());
            person.setBirthDate(personRequest.getBirthDate());

            return person;
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId " + personId + " не найден"));
        
        return save(updPerson, HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить данные по гражданину согласно его ID")
    @DeleteMapping("/persons/{personId}")
    public ResponseEntity<?> deletePost(
        @ApiParam(value = "Идентификатор гражданина", required = true) @PathVariable Long personId) throws ResourceNotFoundException {
        return personRepository.findById(personId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId " + personId + " не найден"));
    }

    private ResponseEntity<PersonDto> save(Person person, HttpStatus statusCode) {

        PersonDto personNew = new PersonDto(personRepository.save(person));
        return new ResponseEntity<>(personNew, statusCode);
    }

    private List<PersonDto> getListDto(List<Person> personList) {

        return personList.stream().map(person -> {return new PersonDto(person);}).collect(Collectors.toList());
    }
}