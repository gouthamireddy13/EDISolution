package com.abc.dashboard.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.abc.dashboard.model.projections.SdBusinessUnitProjection;
import com.abc.dashboard.service.SdMasterDataService;

@RestController
@RequestMapping("/sd/businessUnits")
public class SdMasterDataController {
	
	private static final Logger logger = LogManager.getLogger(SdMasterDataController.class);
	
	@Autowired
	SdMasterDataService sdMasterDataService;
	
	@RequestMapping(method={RequestMethod.GET})
	ResponseEntity<List<?>> getAllBusinessUnits(@RequestParam(name="projection",required=false) String projection)
	{
		logger.debug("SdMasterDataController::getAllBusinessUnits()");
		
		List<?> result = null;
		
		if (projection!=null && projection.compareToIgnoreCase("idAndName")==0)
		{
			result = sdMasterDataService.getAllSdBysinessUnitsSortedByName(SdBusinessUnitProjection.class);
		}
		else
		{
			result = sdMasterDataService.getAllSdBysinessUnitsSortedByName();
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}
	
	@RequestMapping(method={RequestMethod.GET},value={"/{id}"})
	ResponseEntity<?> getBusinessUnit(@PathVariable(name="id") long id, @RequestParam(name="projection",required=false) String projection)
	{
		logger.debug("SdMasterDataController::getBusinessUnit()");
		
		Object result = null;
		
		if (projection!=null && projection.compareToIgnoreCase("idAndName")==0)
		{
			result = sdMasterDataService.findSdBusinessUnitById(id,SdBusinessUnitProjection.class);
		}
		else
		{
			result = sdMasterDataService.findSdBusinessUnitById(id);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}
	
	@RequestMapping(method={RequestMethod.GET},value={"/subunitByGsId"})
	ResponseEntity<List<String>> getAllBusinessSubUnitsForGSID(@RequestParam(name="gsid",required=true) String gsid)
	{
		List<String> result = null;
		
		result = sdMasterDataService.findSubUnitForABCid(gsid);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@RequestMapping(method={RequestMethod.GET},value={"/subunitByLwId"})
	ResponseEntity<String> getAllBusinessSubUnitsForLWID(@RequestParam(name="lwid",required=true) long lwid)
	{
		String result = null;
		
		result = sdMasterDataService.findSubUnitForLwId(lwid);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
