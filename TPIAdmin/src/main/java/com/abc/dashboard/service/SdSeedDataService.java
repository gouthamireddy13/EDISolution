package com.abc.dashboard.service;

import org.springframework.web.multipart.MultipartFile;

import com.abc.dashboard.domain.SdDataLoadResult;
import com.abc.tpi.common.exceptions.TpiValidationException;
import com.abc.tpi.utils.SeedDataInsertStatMsg;

public interface SdSeedDataService {
	
	SdDataLoadResult processSeedDataCSVFile(MultipartFile file, String entityName);
	SeedDataInsertStatMsg loadSdServiceCategory(String line) throws TpiValidationException;
	boolean loadSdBusinessService(String line) throws TpiValidationException;
}
