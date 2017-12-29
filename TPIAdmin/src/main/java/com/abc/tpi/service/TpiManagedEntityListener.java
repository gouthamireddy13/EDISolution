
package com.abc.tpi.service;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.abc.tpi.model.migrator.ChangeType;
import com.abc.tpi.model.poc.TpiManagedEntity;

public class TpiManagedEntityListener {
	
	@PrePersist
	public void postPersist(TpiManagedEntity managedEntity) 
	{
		managedEntity.setChangeType(ChangeType.A);		
	}
		
	@PreUpdate
	public void postUpdate(TpiManagedEntity managedEntity) 
	{
		managedEntity.setChangeType(ChangeType.C);
	}
		
	@PreRemove
	public void postRemove(TpiManagedEntity managedEntity) 
	{
		managedEntity.setChangeType(ChangeType.D);
	}
}
