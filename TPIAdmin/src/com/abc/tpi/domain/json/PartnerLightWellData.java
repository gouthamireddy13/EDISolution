package com.abc.tpi.domain.json;

/**
 * 
 * @author a061313
 * This class is meant to transmit basic Partner Id, LightWellPartner id data to the page
 */

public class PartnerLightWellData {

	private long partnerId;
	private long lwId;
	private String partnerName;
	private String gsId;
	
	public PartnerLightWellData(long partnerId, String partnerName, long lwId, String gsId) {
		super();
		this.partnerId = partnerId;
		this.lwId = lwId;
		this.partnerName = partnerName;
		this.gsId = gsId;
	}
	public long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}
	public long getLwId() {
		return lwId;
	}
	public void setLwId(long lwId) {
		this.lwId = lwId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getGsId() {
		return gsId;
	}
	public void setGsId(String gsId) {
		this.gsId = gsId;
	}

}
