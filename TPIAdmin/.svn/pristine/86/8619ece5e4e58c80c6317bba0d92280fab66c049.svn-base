package com.abc.tpi.model.poc;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.abc.tpi.domain.core.ApprovableEntityEnum;

@MappedSuperclass
public abstract class ApprovableEntity extends TpiManagedEntity 
{
	
	@Column(name="STATE", nullable=false)
	@Enumerated(EnumType.STRING)
	private ApprovableEntityEnum state = null;
	
	@Column(name="DELETED")
	private boolean deleted = false;
	
	@Column(name="TARGET_OBJECT_ID")
	private long targetObjectId;
	
	@Column(name="TARGET_VERSION_NUM")
	private long targetObjectVersionNum;
	
	
	public ApprovableEntityEnum getState() {
		return state;
	}
	public void setState(ApprovableEntityEnum state) {
		this.state = state;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public long getTargetObjectId() {
		return targetObjectId;
	}
	public void setTargetObjectId(long targetObjectId) {
		this.targetObjectId = targetObjectId;
	}
	public long getTargetObjectVersionNum() {
		return targetObjectVersionNum;
	}
	public void setTargetObjectVersionNum(long targetObjectVersionNum) {
		this.targetObjectVersionNum = targetObjectVersionNum;
	}
}
