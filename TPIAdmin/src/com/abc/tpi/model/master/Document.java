package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ABC_TPI_DOCUMENT")

public class Document {

	@Id
	@SequenceGenerator(name="ABC_TPI_DOCUMENT_GEN", sequenceName="ABC_TPI_DOCUMENT_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_DOCUMENT_GEN")	
	@Column(name="DOC_ID")
	private Long id;
	
	@NotNull
	@Column(name="DOC_TYPE",updatable=false,nullable=false,unique=true)
	private String documentType;
	
	@Column(name="DOC_DESC",nullable=true)
	private String documentDescription;
	
	
	public String getDocumentDescription() {
		return documentDescription;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	public Long getId() {
		return id;
	}
	public void setDocumentDescription(String documentCode) {
		this.documentDescription = documentCode;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public static Document newInstance(Document document)
	{
		if (document==null)
		{
			return null;
		}
		Document clonedDocument = new Document();
		clonedDocument.setDocumentDescription(document.getDocumentDescription());
		clonedDocument.setDocumentType(document.getDocumentType());
		return clonedDocument;
	}
}
