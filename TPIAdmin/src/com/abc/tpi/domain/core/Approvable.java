package com.abc.tpi.domain.core;

public interface Approvable<T>
{

	ApprovableEntityEnum getState();
	void setState(ApprovableEntityEnum state);
	boolean isDeleted();
	void setDeleted(boolean deleted);
	T cloneToFInal();
	T updateFinal(T finalObject);
	boolean hasParent();

}
