package com.abc.tpi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.migrator.ServiceSubscriptionApprovalReview;
import com.abc.tpi.service.migrator.MigratorDataService;

@RestController
@RequestMapping("/migrate")
public class MigrationControllerRest {

	@Autowired
	MigratorDataService migratorService;
	
	private static final Logger logger = LogManager.getLogger(MigrationControllerRest.class);
	
	@RequestMapping(method = {RequestMethod.GET})
	public ResponseEntity<List<ServiceSubscriptionApprovalReview>> getSearchResults(@RequestParam(name="srId",required=true) String sriId)
											  throws TpiRepositoryException 
	{
		List<ServiceSubscriptionApprovalReview> result = migratorService.getApprovalReviewForSriId(sriId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
