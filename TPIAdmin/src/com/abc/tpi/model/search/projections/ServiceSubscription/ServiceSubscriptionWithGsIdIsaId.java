package com.abc.tpi.model.search.projections.ServiceSubscription;

public class ServiceSubscriptionWithGsIdIsaId {

	private Long serviceSubscriptionId;
	private Long serviceId;
	private String serviceCategoryName;
	private String partnerName;
	private String prodGsId;
	private String intercompanyFlag;
	
	public ServiceSubscriptionWithGsIdIsaId(
			Long serviceSubscriptionId, 
			Long serviceId, 
			String serviceCategoryName,
			String partnerName, 
			String prodGsId, 
			String prodIsaId,
			String intercompanyFlag) {
		super();
		this.serviceSubscriptionId = serviceSubscriptionId;
		this.serviceId = serviceId;
		this.serviceCategoryName = serviceCategoryName;
		this.partnerName = partnerName;
		this.prodGsId = prodGsId;
		this.prodIsaId = prodIsaId;
		this.intercompanyFlag = intercompanyFlag;
	}
	private String prodIsaId;

	public Long getServiceSubscriptionId() {
		return serviceSubscriptionId;
	}
	public void setServiceSubscriptionId(Long serviceSubscriptionId) {
		this.serviceSubscriptionId = serviceSubscriptionId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceCategoryName() {
		return serviceCategoryName;
	}
	public void setServiceCategoryName(String serviceCategoryName) {
		this.serviceCategoryName = serviceCategoryName;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getProdGsId() {
		return prodGsId;
	}
	public void setProdGsId(String prodGsId) {
		this.prodGsId = prodGsId;
	}
	public String getProdIsaId() {
		return prodIsaId;
	}
	public void setProdIsaId(String prodIsaId) {
		this.prodIsaId = prodIsaId;
	}
	public String getIntercompanyFlag() {
		return intercompanyFlag;
	}
	public void setIntercompanyFlag(String intercompanyFlag) {
		this.intercompanyFlag = intercompanyFlag;
	}

	
}
