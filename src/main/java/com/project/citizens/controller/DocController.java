package com.project.citizens.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.project.citizens.domain.Doc;
import com.project.citizens.dto.DocDto;
import com.project.citizens.repository.DocRepository;
import com.project.citizens.repository.PersonRepository;
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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value="Persons Management System", description="API относящиеся к документам граждан")
public class DocController {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private PersonRepository personRepository;

    @ApiOperation(value = "Просмотр списка всех документов")
    @GetMapping("/persons/docs")
    public List<DocDto> getAllDocs() throws ResourceNotFoundException {
        return getListDto(docRepository.findAll());
    }

    @ApiOperation(value = "Пролучить весь список документов гражданина по его ID")
    @GetMapping("/persons/{personId}/docs")
    public List<DocDto> getAllDocsByPersonId (
        @ApiParam(value = "Идентификатор гражданина", required = true)
        @PathVariable (value = "personId") Long personId) throws ResourceNotFoundException {
        
        return getListDto(docRepository.findByPersonId(personId));
    }

    @ApiOperation(value = "Добавить документ гражданину по его ID")
    @PostMapping("/persons/{personId}/docs")
    public ResponseEntity<DocDto> createDoc(
        @ApiParam(value = "Идентификатор гражданина", required = true)
        @PathVariable (value = "personId") Long personId, 
        @ApiParam(value = "Данные по документу", required = true)
        @Valid @RequestBody DocDto doc) throws ResourceNotFoundException {
        
        return personRepository.findById(personId).map(person -> {
            doc.setPerson(person);
            return save(doc.toEntity(), HttpStatus.CREATED);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId " + personId + " не найден"));        
    }

    @ApiOperation(value = "Обновить данные документа гражданина по ID документа")
    @PutMapping("/persons/docs/{docId}")
    public ResponseEntity<DocDto> updateDoc(
        @ApiParam(value = "Идентификатор документа", required = true)  
        @PathVariable (value = "docId") Long docId, 
        @ApiParam(value = "Данные по документу", required = true)
        @Valid @RequestBody DocDto docRequest) throws ResourceNotFoundException {
        
        return docRepository.findById(docId).map(doc -> {
            doc.setBeginDate(docRequest.getBeginDate());
            doc.setDocNumber(docRequest.getDocNumber());
            doc.setDocType(docRequest.getDocType());
            doc.setEndDate(docRequest.getEndDate());
            doc.setOrgName(docRequest.getOrgName());

            return save(doc, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("DocId " + docId + " не найден"));
    }

    @ApiOperation(value = "Удалить документ гражданина по ID документа")
    @DeleteMapping("/persons/docs/{docId}")
    public ResponseEntity<?> deleteComment(
        @ApiParam(value = "Идентификатор документа", required = true)
        @PathVariable (value = "docId") Long docId) throws ResourceNotFoundException {
        return docRepository.findById(docId).map(doc -> {
            docRepository.delete(doc);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Документ не найден по id " + docId));
    }

    private ResponseEntity<DocDto> save(Doc doc, HttpStatus statusCode) {

        DocDto docNew = new DocDto(docRepository.save(doc));
        return new ResponseEntity<>(docNew, statusCode);
    }

    private List<DocDto> getListDto(List<Doc> docList) {

        return docList.stream().map(doc -> {return new DocDto(doc);}).collect(Collectors.toList());
    }
}