package com.abc.tpi.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.abc.tpi.domain.core.Approvable;
import com.abc.tpi.model.poc.MigrationTracker;
import com.abc.tpi.model.poc.TpiManagedEntity;
import com.abc.tpi.repository.MigrationTrackerRepository;

@Service
public class DirectMigratorImpl implements MigratorService {

	@Autowired
	MigrationTrackerRepository migrationTrackerRepository;

	@Override
	public <T extends TpiManagedEntity> T migrate(Approvable<T> t, JpaRepository<T, Long> destRepository) {
		MigrationTracker tracker = migrationTrackerRepository.findByWipObjectIdAndEntityClass(((TpiManagedEntity) t).getId(),t.getClass().getName());
		
		if (tracker == null)
		{
			T newObject =  t.cloneToFInal();
			newObject = destRepository.save(newObject);
			MigrationTracker newTracker = new MigrationTracker();
			newTracker.setEntityClass(t.getClass().getName());
			newTracker.setWipObjectId(((TpiManagedEntity) t).getId());
			newTracker.setWipVersionNum((((TpiManagedEntity) t).getVersionNum()));
			newTracker.setFinalObjectId(newObject.getId());
			newTracker.setFinalVersionNum(newObject.getVersionNum());
			migrationTrackerRepository.save(newTracker);
		}
		else
		{
			T existingObject = destRepository.findOne(tracker.getFinalObjectId());
			existingObject =  t.updateFinal(existingObject);
			
			existingObject = destRepository.save(existingObject);
			tracker.setFinalObjectId(existingObject.getId());
			tracker.setFinalVersionNum(existingObject.getVersionNum());
			migrationTrackerRepository.save(tracker);
			
		}
		return null;
	}
}
