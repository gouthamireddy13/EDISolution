package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.Document;
@Repository("documentRepository")
public interface DocumentRepository extends JpaRepository<Document, Long> {
	Document findOneByDocumentType(String documentType);
}
