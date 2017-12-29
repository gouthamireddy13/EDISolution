package com.abc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.dashboard.model.SdServiceType;

@Repository("sdServiceTypeRepository")
public interface SdServiceTypeRepository extends JpaRepository<SdServiceType, Long> 
{
	SdServiceType findOneByName(String name);
}
