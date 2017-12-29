package com.abc.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abc.dashboard.model.SdBusinessUnit;
import com.abc.dashboard.model.SdServiceType;
import com.abc.dashboard.repository.SdBusinessUintRepository;
import com.abc.dashboard.repository.SdServiceTypeRepository;

@Service("sdMasterDataService")
public class SdMasterDataServiceImpl implements SdMasterDataService {

	@Autowired
	SdServiceTypeRepository sdServiceTypeRepository;
	
	@Autowired
	SdBusinessUintRepository sdBusinessUnitRepository;
	
	@Override
	public SdServiceType addServiceTypeIfNotExists(SdServiceType serviceType) {
		
		SdServiceType result = null; 
		
		if (serviceType!=null)
		{
			SdServiceType existing = sdServiceTypeRepository.findOneByName(serviceType.getName());
			
			if (existing!=null)
			{
				result = existing;
			}
			else
			{
				result = sdServiceTypeRepository.save(serviceType);
			}
		}
		return result;
	}

	@Override
	public List<SdServiceType> getAllServiceTypes() {

		return sdServiceTypeRepository.findAll();
	}

	@Override
	public SdServiceType findServiceTypeByName(String name) {
		return sdServiceTypeRepository.findOneByName(name);
	}

	@Override
	public SdServiceType saveServiceType(SdServiceType serviceType) {
		
		SdServiceType result = null;
		if (serviceType!=null)
		{
			sdServiceTypeRepository.save(serviceType);
		}
		return result;
	}

	@Override
	public SdBusinessUnit saveSdBusinessUnit(SdBusinessUnit sdBusinessUnit) {		
		return sdBusinessUnitRepository.save(sdBusinessUnit);
	}

	@Override
	public SdBusinessUnit findSdBusinessUnitByName(String name) {
		return sdBusinessUnitRepository.findOneByNameIgnoreCase(name);
	}
	
	@Override
	public SdServiceType findServiceTypeById(Long id) {
		return sdServiceTypeRepository.findOne(id);
	}

	@Override
	public SdBusinessUnit findSdBusinessUnitByNameAndSubUnitName(String name, String subUnitName) {
		return sdBusinessUnitRepository.namedFindByUnitNameAndSubUnitName(name,subUnitName);
	}

	@Override
	public List<SdBusinessUnit> getAllSdBysinessUnitsSortedByName() {
		return sdBusinessUnitRepository.findAll(new Sort(Sort.Direction.DESC, "name"));
	}

	@Override
	public <T> List<T> getAllSdBysinessUnitsSortedByName(Class<T> projection) {
		return sdBusinessUnitRepository.findAllByOrderByName(projection);
	}

	@Override
	public SdBusinessUnit findSdBusinessUnitById(long id) {
		return sdBusinessUnitRepository.findOne(id);
	}

	@Override
	public <T> T findSdBusinessUnitById(long id, Class<T> projection) {
		return sdBusinessUnitRepository.findOneById(id,projection);
	}

	@Override
	public List<String> findSubUnitForABCid(String gsId) {
		return sdBusinessUnitRepository.namedFindSubUnitForABCid(gsId);
	}

	@Override
	public String findSubUnitForLwId(long id) {
		return sdBusinessUnitRepository.namedFindSubUnitForLwId(id);
	}

}
