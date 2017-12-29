package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.abc.tpi.model.master.MapForDropDownProjection;
import com.abc.tpi.model.master.TpiMap;

public interface TpiMapRepository extends JpaRepository<TpiMap, Long> {
	List<MapForDropDownProjection> namedFindMapByServiceTypeId(@Param("serviceTypeId") long serviceId);
	List<MapForDropDownProjection> findAllByOrderByMapName();
	TpiMap findOneById(long id);
	MapForDropDownProjection findAllById(long id);
	TpiMap findOneByMapName(String name);
} 
