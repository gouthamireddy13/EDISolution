/**
 * 
 */
package com.abc.tpi.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.abc.tpi.common.exceptions.TpiValidationException;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.SeedDataLoadService;
import com.abc.tpi.utils.PartnerUtilBean;
import com.abc.tpi.utils.SeedDataInsertStatMsg;
import com.abc.tpi.utils.SeedDataRespMsg;
import com.abc.tpi.utils.TPPUtilBean;
import com.abc.tpi.utils.UploadDataRestResponse;

/**
 * @author ARINDAMSIKDAR
 *
 */
@RestController
public class SeedDataControllerRest {
	
	private static final Logger logger = LogManager.getLogger(SeedDataControllerRest.class);

	@Autowired
	SeedDataLoadService seedDataService;
	
	@Autowired
	MasterDataService masterDataService;
	
	
	@RequestMapping(value = { "/uploadSeedData" }, headers=("content-type=multipart/*"), method = { RequestMethod.POST})
	public ResponseEntity<?> uploadSeedData(@RequestParam("file") MultipartFile file, @RequestParam("entityName") String entityName) 	{
		logger.debug("Invoked SeedDataControllerRest.uploadSeedData()");
		
				
		if (file.isEmpty()) {
            //return new ResponseEntity( "Please select a file!", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body("Please select a file!");
        }
		
		if (entityName == null || entityName.length() == 0) {
            //return new ResponseEntity( "Please provide Entity Name!", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body("Please provide Entity Name!");
        }
		
		logger.debug("Entity Name: " + entityName);	
		
		SeedDataRespMsg seedDataRespMsg = null;
		try {
			seedDataRespMsg = seedDataService.processSeedDataCSVFile(file, entityName);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(seedDataRespMsg != null) {
			UploadDataRestResponse uploadDataRestResponse = new UploadDataRestResponse();
			uploadDataRestResponse.setEntityName(entityName);
			uploadDataRestResponse.setMessage(seedDataRespMsg.getMessage());
			uploadDataRestResponse.setSeedDataInsertStatMsgList(seedDataRespMsg.getSeedDataInsertStatMsgList());
			
			return ResponseEntity.status(HttpStatus.OK).body(uploadDataRestResponse);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = { "/uploadData/{entityName}" }, headers=("content-type=multipart/*"), method = { RequestMethod.POST})
	public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file, @PathVariable("entityName") String entityName) throws IOException
	{
		logger.debug("Invoked SeedDataControllerRest.uploadData()");
		
		
		if (file.isEmpty()) {
            //return new ResponseEntity( "Please select a file!", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body("Please select a file!");
        }
		
		if (entityName == null || entityName.length() == 0) {
            //return new ResponseEntity( "Please provide Entity Name!", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body("Please provide Entity Name!");
        }
		
		logger.debug("Entity Name: " + entityName);	
		
		SeedDataRespMsg seedDataRespMsg = null;
		try {
			seedDataRespMsg = seedDataService.processSeedDataCSVFile(file, entityName);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(seedDataRespMsg != null) {
			UploadDataRestResponse uploadDataRestResponse = new UploadDataRestResponse();
			uploadDataRestResponse.setEntityName(entityName);
			uploadDataRestResponse.setMessage(seedDataRespMsg.getMessage());
			uploadDataRestResponse.setSeedDataInsertStatMsgList(seedDataRespMsg.getSeedDataInsertStatMsgList());
			
			return ResponseEntity.status(HttpStatus.OK).body(uploadDataRestResponse);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	@RequestMapping(value = { "/uploadAbcIdMaintenance" }, method = { RequestMethod.POST})
	public ResponseEntity<?> uploadAbcIdMaintenance(@RequestParam("rowLine") String rowLine)
	{
		logger.debug("Invoked SeedDataControllerRest.uploadAbcIdMaintenance()");
		
		if(rowLine == null || rowLine.length() == 0) {
			logger.debug("Missing RowLine input");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(seedDataService.performUploadAbcIdMaintenance(rowLine));
	}
	
	@RequestMapping(value = { "/triggerToHigherEnv/{entityName}" }, method = { RequestMethod.POST})
	public ResponseEntity<?> triggerToHigherEnv(@PathVariable("entityName") String entityName, @RequestParam("rowLine") String rowLine)
	{
		logger.debug("Invoked SeedDataControllerRest.triggerToHigherEnv()");
		
		if(rowLine == null || rowLine.length() == 0) {
			logger.error("Missing RowLine input");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(entityName == null || entityName.length() == 0) {
			logger.error("Missing EntityName input");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);			
		}
			
		
		return ResponseEntity.status(HttpStatus.OK).body(seedDataService.performUploadThroughAPI(rowLine,entityName));
	}
}