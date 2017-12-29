package com.abc.dashboard.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.abc.dashboard.model.SdBusinessUnit;
import com.abc.dashboard.model.SdServiceType;

public interface SdMasterDataService {
	
	//service types
	SdServiceType addServiceTypeIfNotExists(SdServiceType serviceType);
	List<SdServiceType> getAllServiceTypes();
	SdServiceType findServiceTypeByName(String name);
	SdServiceType saveServiceType(SdServiceType serviceType);
	SdServiceType findServiceTypeById(Long id);
	
	//Business Unit
	SdBusinessUnit saveSdBusinessUnit(SdBusinessUnit sdBusinessUnit);
	SdBusinessUnit findSdBusinessUnitByName(String name);
	SdBusinessUnit findSdBusinessUnitById(long id);	
	<T> T findSdBusinessUnitById(long id, Class<T> projection);	
	SdBusinessUnit findSdBusinessUnitByNameAndSubUnitName(String name, String subUnitName);
	List<SdBusinessUnit> getAllSdBysinessUnitsSortedByName();
	<T> List<T> getAllSdBysinessUnitsSortedByName(Class<T> projection);
	
	//Business SubUnit
	List<String> findSubUnitForABCid(String gsId);
	String findSubUnitForLwId(long id);
	
	
}
