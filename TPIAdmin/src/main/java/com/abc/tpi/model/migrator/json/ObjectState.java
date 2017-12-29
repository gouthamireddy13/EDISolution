package com.abc.tpi.model.migrator.json;

public abstract class ObjectState {

	private String name;
	private long objectId;
	private boolean deleted;
	private boolean isNew;
	private String srId;
	private boolean toBeApproved;
	private boolean isApproved;
	
	public String getName() {
		return name;
	}
	public long getObjectId() {
		return objectId;
	}
	public String getSrId() {
		return srId;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public boolean isNew() {
		return isNew;
	}
	public boolean isToBeApproved() {
		return toBeApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public void setSrId(String srId) {
		this.srId = srId;
	}
	public void setToBeApproved(boolean toBeApproved) {
		this.toBeApproved = toBeApproved;
	}

}
