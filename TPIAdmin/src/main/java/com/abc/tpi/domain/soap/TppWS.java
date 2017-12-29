package com.abc.tpi.domain.soap;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType( XmlAccessType.FIELD )
public class TppWS {
	
	@XmlElement(name="NAME",required=true)
	String name;
	
	@XmlElement(name="TYPE", required=true, type=Short.class)
	short type;
	
	@XmlElementWrapper(name="CONTACTS", required=true)
	@XmlElement(name="CONTACT", required=true)	
	List<ContactWS> contacts;
	
	@XmlElement(name="LW",required=false)
	LW lw;
	
	@XmlElementWrapper(name="TRANSACTIONS",required=false,nillable=true)
	@XmlElement(name="TRANSACTION", required=false)
	List<TransactionWS> transactions;
	
	@XmlElementWrapper(name="PROTOCOLS",required=false,nillable=true)
	@XmlElement(name="PROTOCOL", required=false)
	List<String> protocols;
	
	public List<ContactWS> getContacts() {
		return contacts;
	}
	public LW getLw() {
		return lw;
	}
	public String getName() {
		return name;
	}
	public List<String> getProtocols() {
		return protocols;
	}
	public List<TransactionWS> getTransactions() {
		return transactions;
	}
	public short getType() {
		return type;
	}
	public void setContacts(List<ContactWS> contacts) {
		this.contacts = contacts;
	}
	public void setLw(LW lw) {
		this.lw = lw;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProtocols(List<String> protocols) {
		this.protocols = protocols;
	}
	public void setTransactions(List<TransactionWS> transactions) {
		this.transactions = transactions;
	}
	public void setType(short type) {
		this.type = type;
	}
}
