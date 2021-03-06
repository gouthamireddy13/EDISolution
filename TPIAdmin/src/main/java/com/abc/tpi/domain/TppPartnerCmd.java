package com.abc.tpi.domain;

import java.util.List;

import com.abc.tpi.model.tpp.Transaction;

public class TppPartnerCmd {

	private String name;
	private String contactEmail;
	private String contactTitle;
	private String contactPhone;

	private int type;
	private List<Integer> protocols;
	private List<Transaction> transactions;

	private String testISAId;
	private String testIsaQualifier;
	private String testGsId;

	private String prodISAId;
	private String prodIsaQualifier;
	private String prodGsId;

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public String getName() {
		return name;
	}

	public String getProdGsId() {
		return prodGsId;
	}

	public String getProdISAId() {
		return prodISAId;
	}

	public String getProdIsaQualifier() {
		return prodIsaQualifier;
	}

	public List<Integer> getProtocols() {
		return protocols;
	}

	public String getTestGsId() {
		return testGsId;
	}

	public String getTestISAId() {
		return testISAId;
	}

	public String getTestIsaQualifier() {
		return testIsaQualifier;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public int getType() {
		return type;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProdGsId(String prodGsId) {
		this.prodGsId = prodGsId;
	}

	public void setProdISAId(String prodISAId) {
		this.prodISAId = prodISAId;
	}

	public void setProdIsaQualifier(String prodIsaQualifier) {
		this.prodIsaQualifier = prodIsaQualifier;
	}

	public void setProtocols(List<Integer> protocols) {
		this.protocols = protocols;
	}

	public void setTestGsId(String testGsId) {
		this.testGsId = testGsId;
	}

	public void setTestISAId(String testISAId) {
		this.testISAId = testISAId;
	}

	public void setTestIsaQualifier(String testIsaQualifier) {
		this.testIsaQualifier = testIsaQualifier;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setType(int type) {
		this.type = type;
	}
}
