package com.abc.tpi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abc.tpi.domain.SeedDataImport;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.SeedDataLoadService;
import com.abc.tpi.utils.SeedDataRespMsg;

@Controller
public class SeedDataController {

	@Autowired
	SeedDataLoadService seedDataService;
	
	@Autowired
	MasterDataService masterDataService;
	
	@RequestMapping(value = { "/loadSeedData" }, method = { RequestMethod.POST})
	public ModelAndView uploadSeedData(@RequestParam("file") MultipartFile file, @RequestParam("entityName") String entityName) throws IOException
	{
		ModelAndView model = new ModelAndView("seedData");
		//String message = seedDataService.processSeedDataCSVFile(file, entityName);
		SeedDataRespMsg seedDataRespMsg = seedDataService.processSeedDataCSVFile(file, entityName);
		
		 System.out.println(entityName);

		 	
			model.addObject("entityName", "");
			model.addObject("message",seedDataRespMsg.getMessage()); //UPDATE: ARINDAM SIKDAR: TPI DYNAMIC DATALOAD
			model.addObject("seedData",new SeedDataImport());	
			
			//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
			model.addObject("seedDataInsertStatMsgList", seedDataRespMsg.getSeedDataInsertStatMsgList());
			
			return model;
	}
	
	@RequestMapping(value = { "/loadData" }, method = { RequestMethod.POST})
	public ModelAndView uploadTPIData(@RequestParam("file") MultipartFile file, @RequestParam("entityName") String entityName) throws IOException
	{
		ModelAndView model = new ModelAndView("loadData");
		//String message = seedDataService.processSeedDataCSVFile(file, entityName);
		SeedDataRespMsg seedDataRespMsg = seedDataService.processSeedDataCSVFile(file, entityName);
		
		 System.out.println(entityName);

		 	
			model.addObject("entityName", "");
			model.addObject("message",seedDataRespMsg.getMessage()); //UPDATE: ARINDAM SIKDAR: TPI DYNAMIC DATALOAD
			model.addObject("loadData",new SeedDataImport());	
			
			//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
			model.addObject("seedDataInsertStatMsgList", seedDataRespMsg.getSeedDataInsertStatMsgList());
			
			return model;
	}
	
	@RequestMapping(value = { "/loadDataJson" }, method = { RequestMethod.POST})
	public String uploadTPIDataJson(@RequestParam("file") MultipartFile file, @RequestParam("entityName") String entityName) throws IOException
	{
		
		//String message = seedDataService.processSeedDataCSVFile(file, entityName);
		SeedDataRespMsg seedDataRespMsg = seedDataService.processSeedDataCSVFile(file, entityName);
		

		 	
		
			return seedDataRespMsg.getMessage(); //UPDATE: ARINDAM SIKDAR: TPI DYNAMIC DATALOAD
				
			
			
			
			
	}
}
