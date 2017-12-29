package com.abc.dashboard.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abc.dashboard.domain.SdDataLoadResult;
import com.abc.dashboard.service.SdSeedDataService;
import com.abc.tpi.domain.SeedDataImport;
import com.abc.tpi.service.MasterDataService;

@Controller("sdSeedDataController")
public class SdSeedDataController {

	@Autowired
	SdSeedDataService seedDataService;
	
	@Autowired
	MasterDataService masterDataService;
	
	@RequestMapping(value = { "/loadSeedDataSd" }, method = { RequestMethod.POST})
	public ModelAndView uploadSeedData(@RequestParam("file") MultipartFile file, @RequestParam("entityName") String entityName) throws IOException
	{
		ModelAndView model = new ModelAndView("seedDataSd");
		SdDataLoadResult seedDataRespMsg = seedDataService.processSeedDataCSVFile(file, entityName);
		
		 System.out.println(entityName);

		 	model.addObject("entityName", "");
			model.addObject("linesProcessed",seedDataRespMsg.getLinesProcessed()); //UPDATE: ARINDAM SIKDAR: TPI DYNAMIC DATALOAD
			model.addObject("linesFailed",seedDataRespMsg.getLinesFailed());
			model.addObject("seedDataSd",new SeedDataImport());
			model.addObject("seedDataInsertStatMsgList", seedDataRespMsg.getMessages());
			
			return model;
	}
	
}
