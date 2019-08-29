package com.project.citizens.repository;

import java.util.List;
import java.util.Optional;

import com.project.citizens.domain.Doc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends JpaRepository<Doc, Long> {

    List<Doc> findByPersonId(Long personId);
    Optional<Doc> findByIdAndPersonId(Long id, Long personId);
}