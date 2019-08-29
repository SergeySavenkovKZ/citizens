package com.project.citizens.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.citizens.domain.Person;
import com.project.citizens.domain.Sex;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonPropertyOrder({ "id",  "firstName", "lastName", "middleName", "birthDate", "sex", "location"})
@ApiModel(description = "Информация о гражданине")
public class PersonDto {
    public static final String DATE_FORMAT = "dd.MM.yyyy";

    @ApiModelProperty(notes = "В базе данных генерируемый идентификатор гражданина", position = 0)
    private Long id;

    @NotNull
    @ApiModelProperty(notes = "Имя гражданина", example = "Иван", position = 1)
    private String firstName;

    @NotNull
    @ApiModelProperty(notes = "Фамилия гражданина", example = "Сидоров", position = 2)    
    private String lastName;

    @NotNull
    @ApiModelProperty(notes = "Отчество гражданина", example = "Петрович", position = 3)    
    private String middleName;

    @NotNull
    @ApiModelProperty(notes = "Дата рождения гражданина", example = "01.02.1990", position = 4)
    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT, timezone = "Asia/Almaty")
    private Date birthDate;

    @NotNull
    @ApiModelProperty(notes = "Пол гражданина", example = "Man", position = 5)
    private Sex sex;

    @NotNull
    @ApiModelProperty(notes = "Адрес проживания гражданина", example = "Росссия, г. Москва, ул. Строителей 45", position = 6)
    private String location;

    public PersonDto() {
        // for json serialization
    }

    public PersonDto(Person personEntity) {

        this.setId(personEntity.getId());
        this.setBirthDate(personEntity.getBirthDate());
        this.setFirstName(personEntity.getFirstName());
        this.setLastName(personEntity.getLastName());
        this.setLocation(personEntity.getLocation());
        this.setMiddleName(personEntity.getMiddleName());
        this.setSex(personEntity.getSex());
    }

    public Person toEntity() {

        Person person = new Person();
        person.setBirthDate(birthDate);
        person.setFirstName(firstName);
        person.setId(id);
        person.setLastName(lastName);
        person.setLocation(location);
        person.setMiddleName(middleName);
        person.setSex(sex);

        return person;
    }


    public Long getId() {
        return id;
    }
   
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
 }