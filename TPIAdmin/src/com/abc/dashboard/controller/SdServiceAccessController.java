package com.abc.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;
import com.abc.dashboard.model.SdServiceAccess;
import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.projections.SdServiceAccessProjection;
import com.abc.dashboard.service.PcceAbcBusinessUnitLookupService;
import com.abc.dashboard.service.SdServiceCategoryService;
import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.controller.PartnerControllerRest;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.service.ServiceSubscriptionService;

@RestController
@RequestMapping("/sd/sdServiceAccess")

public class SdServiceAccessController {

	private static final Logger logger = LogManager.getLogger(SdServiceAccessController.class);
	
	@Autowired
	SdServiceCategoryService sdServiceCategoryService;
	
	
	@RequestMapping(method={RequestMethod.GET})
	ResponseEntity<List<?>> getAllServiceAccess(@RequestParam(name="projection",required=false) String projection)
	{
		logger.debug("SdServiceAccessController::getAllServiceAccess");
		List<?> result = null;
		if (projection!=null && projection.compareToIgnoreCase("display")==0)
		{
			result = sdServiceCategoryService.getAllSdServiceAccess(SdServiceAccessProjection.class);	
		}
		else
		{
			result = sdServiceCategoryService.getAllSdServiceAccess(SdServiceAccess.class);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	@RequestMapping(method={RequestMethod.GET},value={"/{id}"})
	ResponseEntity<?> getServiceAccess(@RequestParam(name="projection",required=false) String projection,@PathVariable(name="id") long id)
	{
		logger.debug("SdServiceAccessController::getServiceAccess");
		Object result = null;
		if (projection!=null && projection.compareToIgnoreCase("display")==0)
		{
			result = sdServiceCategoryService.getSdServiceAccessById(id, SdServiceAccessProjection.class);	
		}
		else
		{
			result = sdServiceCategoryService.getSdServiceAccessById(id,SdServiceAccess.class);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);		
	}
	
	@RequestMapping(method={RequestMethod.DELETE},value={"/{id}"}) 
	ResponseEntity<Object> deleteServiceAccess(@PathVariable(name="id") long id) throws TpiRepositoryException {

		logger.debug("Invoked SdServiceAccessController.deleteSdServiceCategoryDef()");

		try 
		{
			sdServiceCategoryService.deleteSdServiceAccessDef(id);
			return ResponseEntity.noContent().build();
		} 
	 	catch (ResourceNotFoundException e) 
		{
	 		return ResponseEntity.notFound().build();
		}
		catch (Exception ex) 
		{
			logger.error(ex);
			logger.error("Failed to Delete SdServiceAccess with ID: . " + id, ex);
			throw new TpiRepositoryException("Failed to delete SdServiceAccess.");
		}
	}
	
	@RequestMapping(method={RequestMethod.POST})
	public ResponseEntity<SdServiceAccess> saveSdServiceAccess(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SdServiceAccess entity) throws TpiRepositoryException {

		Logger logger = LogManager.getLogger(PartnerControllerRest.class);
		logger.debug("Invoked SdServiceAccessController.saveSdServiceAccess()");

		SdServiceAccess result = null;
		try {
			result = sdServiceCategoryService.saveSdServiceAccessWithPCCEInsert(entity); //saveSdServiceAccess(entity);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception ex) {
			logger.error("Failed to Save SdServiceAccess. ", ex);
			throw new TpiRepositoryException("Failed to Save SdServiceAccess.");
		}
	}
	
	
	@RequestMapping(method={RequestMethod.GET},value="/exists")
	ResponseEntity<Boolean> existsFor(@RequestParam(name="sourceId",required=false) String sourceId,
										@RequestParam(name="destId",required=false) String destinationId,
										@RequestParam(name="serviceCategoryDefId", required=false) Long serviceCategoryDefId,
										@RequestParam(name="servicePreamble",required=false) String servicePreamble,
										@RequestParam(name="serviceTypeId",required=false) Long serviceTypeId)
	{
		Boolean result = new Boolean(false);
		SdServiceAccess resultObject = null;
		if(serviceCategoryDefId == null && servicePreamble == null && serviceTypeId == null) {
			resultObject = sdServiceCategoryService.findOneBySourceAndDestinationIdsIgnoreCase(sourceId, destinationId);
		} else {
			resultObject = sdServiceCategoryService.findOneBySrceAndDestIdsAndSrvcPreambleAndSrvcCatDef(sourceId, destinationId, serviceCategoryDefId,servicePreamble, serviceTypeId );
		}
		if (resultObject!=null)
		{
			result = true;
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
}
