package com.abc.dashboard.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.dashboard.model.SdServiceType;
import com.abc.dashboard.service.SdMasterDataService;

@RestController
@RequestMapping("/sd/serviceTypes")
public class SdServiceTypeController {
	
	private static final Logger logger = LogManager.getLogger(SdServiceTypeController.class);
	
	@Autowired
	SdMasterDataService sdMasterDataService;
	
	@RequestMapping(method={RequestMethod.GET})
	ResponseEntity<List<SdServiceType>> getAllServiceTypess()
	{
		logger.debug("SdServiceTypeController::getAllServiceTypess()");
		List<SdServiceType> result = sdMasterDataService.getAllServiceTypes();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
