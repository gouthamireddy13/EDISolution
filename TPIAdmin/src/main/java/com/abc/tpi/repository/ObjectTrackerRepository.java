package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.migrator.ObjectTracker;
@Repository("objectTrackerRepository")
public interface ObjectTrackerRepository extends JpaRepository<ObjectTracker, Long> 
{
	ObjectTracker findOneByClassNameAndSourceId(String className, Long sourceId);
}
