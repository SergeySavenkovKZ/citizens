package com.project.citizens.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.citizens.domain.Doc;
import com.project.citizens.domain.DocType;
import com.project.citizens.domain.Person;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Информация о документе гражданина")
public class DocDto {
    public static final String DATE_FORMAT = "dd.MM.yyyy";

    @ApiModelProperty(notes = "В базе данных генерируемый идентификатор документа", position = 0)
    private Long id;

    @NotNull
    @ApiModelProperty(notes = "Номер документа", example = "N123456789", position = 1)    
    private String docNumber;

    @NotNull
    @ApiModelProperty(notes = "Тип документа", example = "Passport", position = 2)
    private DocType docType;

    @NotNull
    @ApiModelProperty(notes = "Наименование организации выдавшей документ", example = "МВД", position = 3)
    private String orgName;
    
    @NotNull
    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT, timezone = "Asia/Almaty")
    @ApiModelProperty(notes = "Дата выдачи документа", example = "01.02.1990", position = 4)
    private Date beginDate;
    
    @NotNull
    @DateTimeFormat(pattern = DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT, timezone = "Asia/Almaty")
    @ApiModelProperty(notes = "Дата окончания срока действия документа", example = "01.02.2990", position = 5)
    private Date endDate;

    @JsonIgnore
    //@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    //@JsonIdentityReference(alwaysAsId=true)
    //@JsonProperty("person")
    private Person person;

    public DocDto() {
        // for json serialization
    }

    public DocDto(Doc docEntity) {

        this.setBeginDate(docEntity.getBeginDate());
        this.setDocNumber(docEntity.getDocNumber());
        this.setDocType(docEntity.getDocType());
        this.setEndDate(docEntity.getEndDate());
        this.setId(docEntity.getId());
        this.setOrgName(docEntity.getOrgName());
        this.setPerson(docEntity.getPerson());
    }

    public Doc toEntity() {

        Doc doc = new Doc();
        doc.setBeginDate(this.getBeginDate());
        doc.setDocNumber(this.getDocNumber());
        doc.setDocType(this.getDocType());
        doc.setEndDate(this.getEndDate());
        doc.setId(this.getId());
        doc.setOrgName(this.getOrgName());
        doc.setPerson(this.getPerson());

        return doc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}