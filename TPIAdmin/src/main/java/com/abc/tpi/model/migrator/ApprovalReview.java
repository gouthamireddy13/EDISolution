package com.abc.tpi.model.migrator;

public abstract class ApprovalReview {
	
	private String name;
	private long objectId;
	private String srId;
	private boolean deleted;
	private boolean toBeExported;
	private boolean exported;
	private boolean isNew;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getObjectId() {
		return objectId;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public String getSrId() {
		return srId;
	}
	public void setSrId(String srId) {
		this.srId = srId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isToBeExported() {
		return toBeExported;
	}
	public void setToBeExported(boolean toBeExported) {
		this.toBeExported = toBeExported;
	}
	public boolean isExported() {
		return exported;
	}
	public void setExported(boolean exported) {
		this.exported = exported;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}
