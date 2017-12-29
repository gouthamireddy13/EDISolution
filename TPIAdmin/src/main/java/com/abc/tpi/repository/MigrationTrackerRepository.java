package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.poc.MigrationTracker;
@Repository
public interface MigrationTrackerRepository extends JpaRepository<MigrationTracker, Long> {
	MigrationTracker findByWipObjectIdAndWipVersionNumAndEntityClass(long objectId,long versionNum,String className);
	MigrationTracker findByWipObjectIdAndEntityClass(long objectId,String className);

}
