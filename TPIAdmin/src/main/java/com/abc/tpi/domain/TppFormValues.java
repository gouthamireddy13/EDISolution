package com.abc.tpi.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.repository.DocumentRepository;

public class TppFormValues {

	private List<TpiDirection> directions;
	private List<Document> documentTypes;
	private List<Version> versions;
	private List<Protocol> protocols;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	public TppFormValues()
	{
		
	}
	
	public List<TpiDirection> getDirections() {
		return directions;
	}
	public List<Document> getDocumentTypes() {
		return documentTypes;
	}
	public List<Protocol> getProtocols() {
		return protocols;
	}
	public List<Version> getVersions() {
		return versions;
	}
	public void setDirections(List<TpiDirection> directions) {
		this.directions = directions;
	}
	public void setDocumentTypes(List<Document> documentTypes) {
		this.documentTypes = documentTypes;
	}
	public void setProtocols(List<Protocol> protocols) {
		this.protocols = protocols;
	}
	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}
	
	public void loadData()
	{
		documentTypes = documentRepository.findAll();
	}
}
