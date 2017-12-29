package com.abc.tpi.model.search.projections.ServiceSubscription;

public class BusinessServiceWithParentInfo {

	private Long serviceSubscripionId;
	private String partnerName;
	private String serviceCategoryName;
	private Long serviceId;
	private Long businessServiceId;
	private String businessServiceName;
	private String srId;
	private String notes;
	
	
	public BusinessServiceWithParentInfo(Long serviceSubscripionId, String partnerName, String serviceCategoryName, Long serviceId, Long businessServiceId,
			String businessServiceName, String srId, String notes) {
		super();
		this.serviceSubscripionId = serviceSubscripionId;
		this.serviceId = serviceId;
		this.businessServiceId = businessServiceId;
		this.businessServiceName = businessServiceName;
		this.srId = srId;
		this.notes = notes;
		this.partnerName =partnerName;
		this.serviceCategoryName = serviceCategoryName;
	}

	public Long getServiceSubscripionId() {
		return serviceSubscripionId;
	}

	public void setServiceSubscripionId(Long serviceSubscripionId) {
		this.serviceSubscripionId = serviceSubscripionId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getBusinessServiceId() {
		return businessServiceId;
	}

	public void setBusinessServiceId(Long businessServiceId) {
		this.businessServiceId = businessServiceId;
	}

	public String getBusinessServiceName() {
		return businessServiceName;
	}

	public void setBusinessServiceName(String businessServiceName) {
		this.businessServiceName = businessServiceName;
	}

	public String getSrId() {
		return srId;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getServiceCategoryName() {
		return serviceCategoryName;
	}

	public void setServiceCategoryName(String serviceCategoryName) {
		this.serviceCategoryName = serviceCategoryName;
	}

}
