package com.abc.dashboard.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.dashboard.model.SdBusinessSubUnit;
import com.abc.dashboard.model.SdBusinessUnit;
@Repository
public interface SdBusinessUintRepository extends JpaRepository<SdBusinessUnit, Long> 

{
	
	SdBusinessUnit findOneByNameIgnoreCase(String name);
	SdBusinessUnit namedFindByUnitNameAndSubUnitName(@Param ("name") String name ,@Param ("subUnitName") String subUnitName);
	List<SdBusinessSubUnit> namedFindSubUnitsByUnitName(@Param ("name") String name);
	<T> List<T> findAllByOrderByName(Class<T> projection);
	<T> T findOneById(@Param ("id") long id,Class<T> projection);
	List<String> namedFindSubUnitForABCid(@Param ("gsId") String gsId);
	String namedFindSubUnitForLwId(@Param ("id") long id);
	
}
