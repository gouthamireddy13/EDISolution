package com.abc.tpi.model.search.projections.ServiceSubscription;

public class BusinessServiceForSrId {

	private Long serviceSubscripionId;
	private Long serviceId;
	private Long businessServiceId;
	private String businessServiceName;
	private String srId;
	private String notes;
	
	
	public BusinessServiceForSrId(Long serviceSubscripionId, Long serviceId, Long businessServiceId,
			String businessServiceName, String srId, String notes) {
		super();
		this.serviceSubscripionId = serviceSubscripionId;
		this.serviceId = serviceId;
		this.businessServiceId = businessServiceId;
		this.businessServiceName = businessServiceName;
		this.srId = srId;
		this.notes = notes;
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

}
