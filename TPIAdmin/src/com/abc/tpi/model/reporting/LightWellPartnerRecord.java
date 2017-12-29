package com.abc.tpi.model.reporting;

public class LightWellPartnerRecord {

	private long partnerId;
	private long lwId;
	private String id = "";
	private String clientId = "";
	private String b2bIdentifier = "";
	private String orgName = "";
	private String notes = "";
	private String identityExtenstions = "";
	private String activeFlag = "";
	
	
	
	public LightWellPartnerRecord(String b2bIdentifier, String orgName) {
		super();
		this.b2bIdentifier = b2bIdentifier;
		this.orgName = orgName;
	}

	public LightWellPartnerRecord(long partnerId, long lwId, String b2bIdentifier, String orgName) {
		super();
		this.partnerId = partnerId;
		this.lwId = lwId;
		this.b2bIdentifier = b2bIdentifier;
		this.orgName = orgName;
	}

	public LightWellPartnerRecord(String id, String clientId, String b2bIdentifier, String orgName, String notes,
			String identityExtenstions, String activeFlag) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.b2bIdentifier = b2bIdentifier;
		this.orgName = orgName;
		this.notes = notes;
		this.identityExtenstions = identityExtenstions;
		this.activeFlag = activeFlag;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}
	public String getB2bIdentifier() {
		return b2bIdentifier;
	}
	public String getClientId() {
		return clientId;
	}
	public String getId() {
		return id;
	}
	public String getIdentityExtenstions() {
		return identityExtenstions;
	}
	public long getLwId() {
		return lwId;
	}
	public String getNotes() {
		return notes;
	}
	public String getOrgName() {
		return orgName;
	}
	public long getPartnerId() {
		return partnerId;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public void setB2bIdentifier(String b2bIdentifier) {
		this.b2bIdentifier = b2bIdentifier;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setIdentityExtenstions(String identityExtenstions) {
		this.identityExtenstions = identityExtenstions;
	}

	public void setLwId(long lwId) {
		this.lwId = lwId;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}
	
	
}
