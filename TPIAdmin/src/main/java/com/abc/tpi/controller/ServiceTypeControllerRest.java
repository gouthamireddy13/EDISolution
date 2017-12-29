 package com.abc.tpi.controller;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.service.EnvironmentService;
import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.service.ServiceTypeProjectionIdName;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.ServiceSubscriptionService;

@RestController
@RequestMapping("/serviceTypes")
public class ServiceTypeControllerRest {
	
	private final ServiceTypeRepository serviceTypeRepository;
	private final ServiceSubscriptionService serviceSubscriptionService;
	private final MasterDataService masterDataService;
	
	@Autowired
	EnvironmentService environmentService;
	
	private static final Logger logger = LogManager.getLogger(ServiceTypeControllerRest.class);
	
	@Autowired
	ServiceTypeControllerRest(ServiceTypeRepository serviceTypeRepository, 
								ServiceSubscriptionService serviceSubscriptionService,
								MasterDataService masterDataService)
	{
		this.serviceTypeRepository = serviceTypeRepository;
		this.serviceSubscriptionService = serviceSubscriptionService;
		this.masterDataService = masterDataService;
	}
	
	@RequestMapping(method={RequestMethod.GET})
	
	ResponseEntity<?> getServiceTypes(@RequestParam(name="projection",required=false) String projection, 
			@RequestParam(name="serviceCat", required=false) Long serviceCategoryId, 
			@RequestParam(name="tpp", required=false) Long tppId,
			@RequestParam(name="scId",required=false) Long scId) throws TpiRepositoryException
	{
		
		Object result = null;
		logger.debug("Invoked LookUpDataControllerRest.getServiceTypes()");
		
		if (serviceCategoryId!=null && tppId!=null)
		{
			logger.debug("ServiceCategory Id :" + serviceCategoryId);
			logger.debug("Tpp ID:" + tppId);

			result = serviceSubscriptionService.getBusinessServiceForTppAndServiceCat(serviceCategoryId, tppId);	
		
		}
		else if (serviceCategoryId!=null && tppId==null)
		{
			logger.debug("ServiceCategory Id :" + serviceCategoryId);
			
			if (projection!=null && projection.compareTo("idAndNameProjection")==0)
			{
				result = masterDataService.getServiceTypesForServiceCategory(serviceCategoryId,ServiceTypeProjectionIdName.class);
			}
			else
			{
				result = masterDataService.getServiceTypesForServiceCategory(serviceCategoryId);
			}
		}
		else
		{
			if (projection!=null && projection.compareTo("idAndNameProjection")==0)
			{
				result = masterDataService.findAllServiceTypes(ServiceTypeProjectionIdName.class);
			}
			else
			{			
				result = masterDataService.findAllServiceTypes();
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}

	@RequestMapping(value={"/{id}"},method={RequestMethod.GET})
	ResponseEntity<ServiceType> getServiceType(@PathVariable(name="id") long id)
	{
		ServiceType result = masterDataService.findServiceTypeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}
	
	@RequestMapping(value={"/{id}/maps"},method={RequestMethod.GET})
	ResponseEntity<Collection<TpiMap>> getServiceTypeMaps(@PathVariable(name="id") long id)
	{
		Collection<TpiMap> result = masterDataService.findServiceTypeById(id).getMaps();
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}
	
	@RequestMapping(value={"/{id}/maps/{mapId}"},method={RequestMethod.GET})
	ResponseEntity<TpiMap> getServiceMap(@PathVariable(name="id",required=true) Long id, @PathVariable(name="mapId",required=true) Long mapId)
	{
		TpiMap result = serviceTypeRepository.namedFindMapForServiceTypeById(id, mapId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@RequestMapping(value={"/{id}/maps"},method={RequestMethod.POST})
	void addMap(@PathVariable(name="id") long id, @RequestBody TpiMap map) throws TpiRepositoryException
	{
		try
		{
			logger.debug("Addming new Map to ServiceType with id : " + id);
			ServiceType result = masterDataService.addMapToServiceType(id,map);
			
			EnvironmentInfo envInfoForServiceAccess = environmentService.findEnvironmentInfoByParamName("READONLY_DB");
			if(envInfoForServiceAccess != null && envInfoForServiceAccess.getParamVal().length() != 0 && envInfoForServiceAccess.getParamVal().equalsIgnoreCase("N")) {
				if(result != null) {	//Add for trigger to higher environment
					masterDataService.addMapToServiceTypeInProd(result, map);
					logger.debug("Confirmation if upload to higher environment is successfull: ");
				}
			}
		}
		catch (Exception ex)
		{
			throw new TpiRepositoryException("Failed to add Map to Service Type. Exception : " + ex.getMessage());
		}
		
	}
	
	@RequestMapping(value={"/{id}/maps"},method={RequestMethod.DELETE})
	void deleteMap(@PathVariable(name="id") long id, @RequestBody TpiMap map) throws TpiRepositoryException
	{
		try
		{
			logger.debug("Removing Map from the ServiceType with id : " + id + ". Map id + " + map.getId());
			masterDataService.deleteMapForServiceType(id,map);
		}
		catch (Exception ex)
		{
			throw new TpiRepositoryException("Failed to remove Map to Service Type. Exception : " + ex.getMessage());
		}
	}
	
}
