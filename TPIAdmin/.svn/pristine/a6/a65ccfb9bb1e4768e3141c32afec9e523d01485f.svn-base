package com.abc.tpi.domain.soap;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType( XmlAccessType.FIELD )
public class PartnerWS {
	
	@XmlElement(name="NAME",required=true)
	private String name;
	
	@XmlElement(name="GROUP",required=true)
	private String group;
	
	@XmlElement(name="SUBGROUP",required=true)
	private String subGroup;
	
	@XmlElementWrapper(name="CONTACTS",required=true,nillable=false)
	@XmlElement(name="CONTACT", required=true)
	private List<ContactWS> contacts;
	
	public List<ContactWS> getContacts() {
		return contacts;
	}
	public String getGroup() {
		return group;
	}
	public String getName() {
		return name;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setContacts(List<ContactWS> contacts) {
		this.contacts = contacts;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	
}
