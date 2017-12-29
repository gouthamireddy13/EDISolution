package com.abc.tpi.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.tpi.domain.core.Approvable;
import com.abc.tpi.model.poc.TpiManagedEntity;

public interface MigratorService {
	<T extends TpiManagedEntity> T migrate(Approvable<T> t, JpaRepository<T, Long> destRepository);
	
}


