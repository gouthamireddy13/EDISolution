package com.abc.tpi.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.abc.dashboard.model.PcceAbcBusinessService;
import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;
import com.abc.dashboard.model.SdBusinessService;
import com.abc.dashboard.model.SdBusinessSubUnit;
import com.abc.dashboard.model.SdBusinessUnit;
import com.abc.dashboard.model.SdServiceAccess;
import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.SdServiceType;
import com.abc.dashboard.model.SdYesNo;
import com.abc.dashboard.repository.SdBusinessUintRepository;
import com.abc.dashboard.service.PcceAbcBusinessLookupService;
import com.abc.dashboard.service.PcceAbcBusinessUnitLookupService;
import com.abc.dashboard.service.SdSeedDataService;
import com.abc.dashboard.service.SdServiceCategoryService;
import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.service.EnvironmentService;
import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.common.exceptions.TpiValidationException;
import com.abc.tpi.model.master.Ack;
import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.PartnerType;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.partner.ContactDetail;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.partner.PartnerGroup;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.CompanyEnum;
import com.abc.tpi.model.service.PartnerCategoryEnum;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.Transaction;
import com.abc.tpi.repository.PartnerRepository;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.repository.TpiMapRepository;
import com.abc.tpi.utils.ABCIDReturnMsgBean;
import com.abc.tpi.utils.SSReturnMsgBean;
import com.abc.tpi.utils.AppConstants;
import com.abc.tpi.utils.ComplianceMapReturnMsgBean;
import com.abc.tpi.utils.PartnerUtilBean;
import com.abc.tpi.utils.SAReturnMsgBean;
import com.abc.tpi.utils.SeedDataInsertStatMsg;
import com.abc.tpi.utils.SeedDataRespMsg;
import com.abc.tpi.utils.TPPReturnMsgBean;
import com.abc.tpi.utils.TPPUtilBean;
import com.abc.user.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Service
public class SeedDataLoadServiceImpl implements SeedDataLoadService {

	private static final Logger logger = LogManager.getLogger(SeedDataLoadServiceImpl.class);

	@Autowired
	SeedDataLoadService seedDataService;

	@Autowired
	MasterDataService masterDataService;

	@Autowired
	PartnerGroupService partnerGroupService;

	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;

	@Autowired
	TpiMapRepository tpiMapRepository;

	@Autowired
	ServiceTypeRepository serviceTypeRepository;

	@Autowired
	com.abc.dashboard.service.SdMasterDataService sdMasterDataService;

	//Added by Arindam Sikdar for Dynamic Data Load process
	@Autowired
	PartnerService partnerService;

	//Added by Arindam Sikdar for Dynamic Data Load process
	@Autowired
	TppService tppService;

	@Autowired
	SdServiceCategoryService sdServiceCategoryService;

	//Added by Pappu Prasad for Dynamic Data Load process
	@Autowired
	PartnerRepository partnerRepository;

	@Autowired
	SdSeedDataService sdSeedDataService;

	@Autowired
	EnvironmentService environmentService;

	@Autowired
	PcceAbcBusinessUnitLookupService pcceAbcBusinessUnitLookupService;
	
	@Autowired
	PcceAbcBusinessLookupService pcceAbcBusinessLookupService;
	
	@Autowired
	SdBusinessUintRepository sdBusinessUnitRepository;

	@Override
	public Delimiter getDelimiterFromCsvString(String delimiter) {
		// Delimiter definition expects 2 values: CODE and DESCRIPTION
		Delimiter delim = null;
		List<String> items = Arrays.asList(delimiter.split("\\s*,\\s*"));
		if (items.size() == 6) {
			delim = new Delimiter();
			delim.setDelimiter(items.get(0));
			delim.setDescription(items.get(1));
			delim.setSegment(Boolean.parseBoolean(items.get(2)));
			delim.setElement(Boolean.parseBoolean(items.get(3)));
			delim.setComposite(Boolean.parseBoolean(items.get(4)));
			delim.setRepeat(Boolean.parseBoolean(items.get(5)));

		}
		return delim;
	}

	@Override
	public Ack getAckFromCsvString(String ack) {
		Ack newAck = null;
		List<String> items = Arrays.asList(ack.split("\\s*,\\s*"));
		if (items.size() == 2) {
			newAck = new Ack();
			newAck.setAckValue(items.get(0));
			newAck.setDescription(items.get(1));
		}
		return newAck;
	}

	@Override
	public Direction getDirectionFromCsvString(String direction) {
		Direction newDireciton = null;
		List<String> items = Arrays.asList(direction.split("\\s*,\\s*"));
		if (items.size() == 2) {
			newDireciton = new Direction();
			newDireciton.setDirectionCode(items.get(0));
			newDireciton.setDirectionDescription(items.get(1));
		}
		return newDireciton;
	}

	@Override
	public Document getDocumentFromCsvString(String document) {
		Document newDocument = null;
		List<String> items = Arrays.asList(document.split("\\s*,\\s*"));
		if (items.size() == 2) {
			newDocument = new Document();
			newDocument.setDocumentType(items.get(0));
			newDocument.setDocumentDescription(items.get(1));
		}
		return newDocument;
	}

	@Override
	public PartnerType getPartnerTypeFromCsvString(String partnerType) {
		PartnerType newPartnerType = null;
		List<String> items = Arrays.asList(partnerType.split("\\s*,\\s*"));
		if (items.size() == 2) {
			newPartnerType = new PartnerType();
			newPartnerType.setTypeCode(items.get(0));
			newPartnerType.setTypeDescription(items.get(1));
		}
		return newPartnerType;
	}

	@Override
	public Protocol getProtocolFromCcvString(String protocol) {

		Protocol entity = null;
		List<String> items = Arrays.asList(protocol.split("\\s*,\\s*"));
		if (items.size() == 2) {
			entity = new Protocol();
			entity.setProtocolType(items.get(0));
			entity.setProtocolDescription(items.get(1));
		}
		return entity;
	}

	@Override
	public TppType getTppTypeFromCsvString(String tppType) {
		TppType entity = null;
		List<String> items = Arrays.asList(tppType.split("\\s*,\\s*"));
		if (items.size() == 2) {
			entity = new TppType();
			entity.setTypeCode(Short.decode(items.get(0)));
			entity.setDescription(items.get(1));
		}
		return entity;
	}

	@Override
	public Version getVersionFromCsvString(String version) {
		Version entity = null;
		List<String> items = Arrays.asList(version.split("\\s*,\\s*"));
		if (items.size() == 2) {
			entity = new Version();
			entity.setVersionNumber(Integer.decode(items.get(0)));
			entity.setVersionDescription(items.get(1));
		}
		return entity;
	}

	@Override
	public SeedDataRespMsg processSeedDataCSVFile(MultipartFile file, String entityName) throws IOException {

		int counterCreated = 0;
		int counterUpdated = 0;

		//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
		int counterSkipped = 0;
		SeedDataRespMsg seedDataRespMsg = new SeedDataRespMsg();
		ArrayList<SeedDataInsertStatMsg> seedDataInsertStatMsgList = new ArrayList<SeedDataInsertStatMsg>();


		String message= "Processing " + entityName;
		logger.debug("Prcessing Seed Data");

		seedDataRespMsg.setMessage(message); //Added by Arindam Sikdar for Dynamic Data Load process - Partner Load

		BufferedReader reader = null;

		if (file != null && entityName!=null && !file.isEmpty()) 
		{
			//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
			logger.debug("File Name: " + file.getOriginalFilename());
			if(entityName != null && entityName.equalsIgnoreCase("PROMOTION")) {
				SeedDataRespMsg result = processSeedDataMultiTabXlsxFile(file,entityName);
				seedDataInsertStatMsgList = result.getSeedDataInsertStatMsgList();
				message = result.getMessage();
			} else {
				if(file.getOriginalFilename().contains(".xlsx")) {
					logger.debug("Inside DYNAMIC DATA LOAD Loop");

					//FileInputStream excelFile = new FileInputStream(file.getInputStream());
					Workbook workbook = new XSSFWorkbook(file.getInputStream());
					Sheet datatypeSheet = workbook.getSheetAt(0);
					Iterator<Row> iterator = datatypeSheet.iterator();
					List<String> partnerList = new ArrayList();
					List<String> tppList = new ArrayList();
					String rowLine = null;
					StringBuffer sb = null;
					int lastColNum = 0;
					//int firstColNum = 0;
					boolean isHeaderRow = true;
					while (iterator.hasNext()) {
						rowLine = "";
						sb = new StringBuffer();
						Row currentRow = iterator.next();
						if(isHeaderRow) {
							lastColNum = currentRow.getLastCellNum();
							isHeaderRow = false;
							logger.debug("Skipping Hearder Row Number: " + currentRow.getRowNum());
							continue;
						}
						for(int i=0; i < lastColNum; i++) {
							//Iterator<Cell> cellIterator = currentRow.iterator();
							//while (cellIterator.hasNext()) {
							Cell currentCell = currentRow.getCell(i, Row.RETURN_NULL_AND_BLANK); //cellIterator.next();
							if(currentCell != null) {
								switch(currentCell.getCellType()) 
								{
								case XSSFCell.CELL_TYPE_STRING:
									sb.append(currentCell.getStringCellValue()+",");
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									sb.append(new Double(currentCell.getNumericCellValue()).longValue()+",");
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									sb.append(",");
									break;	
								case XSSFCell.CELL_TYPE_BOOLEAN:
									sb.append(Boolean.toString(currentCell.getBooleanCellValue())+",");
									break;								
								default:
									sb.append(",");
									break;

								}
							} else {
								sb.append(",");
							}

						}
						rowLine = sb.toString();
						logger.debug("RowLine: " + rowLine);

						SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();

						String entityNameUpper = entityName.toUpperCase();
						logger.debug("Processing data for : " + entityNameUpper); 

						switch (entityNameUpper) 
						{
						case "ACK":  
							if (loadACK(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;
						case "DELIMITER":  
							if (loadDelimiter(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;
						case "DIRECTION":  
							if (loadDirection(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;						
						case "DOCUMENT":  
							if (loadDocument(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;
						case "MAP":
							if (loadMap(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;	
						case "PARTNER_TYPE":  
							if (loadPartnerType(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;					
						case "PROTOCOL":  
							if (loadProtocol(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;
						case "TPP_TYPE":  
							if (loadTppType(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;
						case "VERSION":  
							if (loadVersion(rowLine))
							{
								seedDataInsertStatMsg.setReturnFlag(0);
							}
							else
							{
								seedDataInsertStatMsg.setReturnFlag(1);
							}
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							break;
							//========
						case "SDSERVICECATEGORY":  
							try {
								if (sdSeedDataService.loadSdBusinessService(rowLine))
								{
									seedDataInsertStatMsg.setReturnFlag(0);
								}
								else
								{
									seedDataInsertStatMsg.setReturnFlag(1);
								}
							} catch (TpiValidationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						case "SDBUSINESSSERVICE":  
							try {
								SeedDataInsertStatMsg seedDataInsertStatMsg1 = sdSeedDataService.loadSdServiceCategory(rowLine);
								//if (sdSeedDataService.loadSdServiceCategory(rowLine))
								if (seedDataInsertStatMsg1.isNew())
								{
									seedDataInsertStatMsg.setReturnFlag(0);
								}
								else
								{
									seedDataInsertStatMsg.setReturnFlag(1);
								}
								
								
							} catch (TpiValidationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
							//==========			
						case "PARTNER":  
							//seedDataInsertStatMsg = loadPartner(rowLine);
							PartnerUtilBean partnerUtilBean = loadPartnerForPromotion(rowLine, partnerList);
							seedDataInsertStatMsg = partnerUtilBean.getSeedDataInsertStatMsg();
							if(partnerUtilBean.getPartnerName() != null && partnerUtilBean.getPartnerName().length() > 0) {
								partnerList.add(partnerUtilBean.getPartnerName());
							}
							break;        
						case "TPP":  
							//seedDataInsertStatMsg = loadTPP(rowLine);
							TPPUtilBean tppUtilBean = loadTPPForPromotion(rowLine, tppList);						
							seedDataInsertStatMsg = tppUtilBean.getSeedDataInsertStatMsg();
							if(tppUtilBean.getTppName() != null && tppUtilBean.getTppName().length() > 0) {
								tppList.add(tppUtilBean.getTppName());
							}
							int rowNo = currentRow.getRowNum()+1;
							logger.debug("<------------------------------------DEBUG DETAILS:START---------------------------------------------->");
							logger.debug("Row Number: " + rowNo);
							logger.debug("Row Line: " + rowLine);
							logger.debug("Row Status(0-Insert, 1-Update, 2-Skip): " + seedDataInsertStatMsg.getReturnFlag());
							logger.debug("Row Status Message(for 2-Skip): " + seedDataInsertStatMsg.getStatusMsg());
							logger.debug("<------------------------------------DEBUG DETAILS:END---------------------------------------------->");
							break; 
						case "ABCIDMAINTENANCE":  
							seedDataInsertStatMsg = loadIDMaintenance(rowLine);
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							int rowNo1 = currentRow.getRowNum()+1;
							logger.debug("<------------------------------------DEBUG DETAILS:START---------------------------------------------->");
							logger.debug("Row Number: " + rowNo1);
							logger.debug("Row Line: " + rowLine);
							logger.debug("Row Status(0-Insert, 1-Update, 2-Skip): " + seedDataInsertStatMsg.getReturnFlag());
							logger.debug("Row Status Message(for 2-Skip): " + seedDataInsertStatMsg.getStatusMsg());
							logger.debug("<------------------------------------DEBUG DETAILS:END---------------------------------------------->");
							break; 
						case "COMPLIANCE_MAP":  
							seedDataInsertStatMsg = loadComplianceMap(rowLine);
							if(seedDataInsertStatMsg != null && (seedDataInsertStatMsg.getReturnFlag() == 0 || seedDataInsertStatMsg.getReturnFlag() == 1)) {
								seedDataInsertStatMsg = makeApiCallForUploadToHigherEnv(rowLine, entityNameUpper);
							}
							int rowNoCMap = currentRow.getRowNum()+1;
							logger.debug("<------------------------------------DEBUG DETAILS:START---------------------------------------------->");
							logger.debug("Row Number: " + rowNoCMap);
							logger.debug("Row Line: " + rowLine);
							logger.debug("Row Status(0-Insert, 1-Update, 2-Skip): " + seedDataInsertStatMsg.getReturnFlag());
							logger.debug("Row Status Message(for 2-Skip): " + seedDataInsertStatMsg.getStatusMsg());
							logger.debug("<------------------------------------DEBUG DETAILS:END---------------------------------------------->");
							break; 
						case "SERVICESUBSCRIPTION":  
							seedDataInsertStatMsg = loadServiceSubscription(rowLine);
							int rowNoSs = currentRow.getRowNum()+1;
							logger.debug("<------------------------------------DEBUG DETAILS:START---------------------------------------------->");
							logger.debug("Row Number: " + rowNoSs);
							logger.debug("Row Line: " + rowLine);
							logger.debug("Row Status(0-Insert, 1-Update, 2-Skip): " + seedDataInsertStatMsg.getReturnFlag());
							logger.debug("Row Status Message(for 2-Skip): " + seedDataInsertStatMsg.getStatusMsg());
							logger.debug("<------------------------------------DEBUG DETAILS:END---------------------------------------------->");
							break; 
						case "SDSERVICEACCESS":
							seedDataInsertStatMsg = loadServiceAccess(rowLine);
							int rowNoSa = currentRow.getRowNum()+1;
							logger.debug("<------------------------------------DEBUG DETAILS:START---------------------------------------------->");
							logger.debug("Row Number: " + rowNoSa);
							logger.debug("Row Line: " + rowLine);
							logger.debug("Row Status(0-Insert, 1-Update, 2-Skip): " + seedDataInsertStatMsg.getReturnFlag());
							logger.debug("Row Status Message(for 2-Skip): " + seedDataInsertStatMsg.getStatusMsg());
							logger.debug("<------------------------------------DEBUG DETAILS:END---------------------------------------------->");
							break; 
						default:
							seedDataInsertStatMsg.setReturnFlag(2);
							seedDataInsertStatMsg.setStatusMsg("Incorrect Entity Name.");
							break;
						}
						int rowCount = currentRow.getRowNum()+1;
						seedDataInsertStatMsg.setRowNum(rowCount);
						if (seedDataInsertStatMsg.getReturnFlag() == 0)
						{
							counterCreated++;
						}
						else if(seedDataInsertStatMsg.getReturnFlag() == 1)
						{
							counterUpdated++;
						}
						else 
						{
							counterSkipped++;
							seedDataInsertStatMsgList.add(seedDataInsertStatMsg);
						}

					}

				}
				else 
				{
					try 
					{
						reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

						while (reader.ready()) 
						{
							String line = reader.readLine();

							logger.debug(line);
							String entityNameUpper = entityName.toUpperCase();
							logger.debug("Processing data for : " + entityNameUpper); 

							switch (entityNameUpper) 
							{
							case "ACK":  
								if (loadACK(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "DELIMITER":  
								if (loadDelimiter(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "DOCUMENT":  
								if (loadDocument(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;

							case "DIRECTION":  
								if (loadDirection(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "PROTOCOL":  
								if (loadProtocol(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "TPP_TYPE":  
								if (loadTppType(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "VERSION":  
								if (loadVersion(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "PARTNER_GROUP":  
								if (loadPartnerGroup(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;	
							case "SERVICE_TYPE":  
								if (loadServiceType(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "SERVICE_CATEGORY":  
								if (loadServiceCategoryLightWellPartner(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "MAP":
								if (loadMap(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "SERVICE_TYPE_MAP":
								if (loadMapsForServiceCategory(line)!=null)
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;
							case "BUSINESS_UNIT":

							{
								if (loadSdBusinessUntiFromString(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
							}
							break;
							case "SD_SERVICE_CATEGORY_LOOKUP":

								try {
									if (loadServiceCategoryDefFromText(line))
									{
										counterCreated++;
									}
									else
									{
										counterUpdated++;
									}
								} catch (TpiValidationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							case "SD_SERVICE_TYPE":

								if (loadSdServiceTypeFromString(line))
								{
									counterCreated++;
								}
								else
								{
									counterUpdated++;
								}
								break;      
							default:
								counterSkipped++;
								break;
							}
						}
					}

					finally 
					{
						if (reader != null)
							reader.close();
					}

				} // END of ELSE Loop added by Arindam Sikdar for Dynamic Data Load process - Partner Load
				message = "Total created: " + counterCreated + " Total updated: " + counterUpdated + " Total skipped: " + counterSkipped;
			} //END of ELSE PROMOTION Loop
		}
		else
		{
			logger.warn("Invalid file or Entity Name supplied");
		}


		seedDataRespMsg.setMessage(message);
		seedDataRespMsg.setSeedDataInsertStatMsgList(seedDataInsertStatMsgList);
		return seedDataRespMsg;
		//return message;
	}

	private boolean loadMap(String line)
	{
		boolean isNew = true;

		TpiMap map = seedDataService.getMapFromCsvString(line);
		TpiMap existingMap = masterDataService.findMapByName(map.getMapName());
		if (existingMap != null)
		{
			existingMap.setMapName(map.getMapName());
			existingMap.setMapDescription(map.getMapDescription());
			masterDataService.saveMap(existingMap);
			isNew= false;
		}
		else
		{
			masterDataService.saveMap(map);
			isNew = true;
		}
		return isNew;

	}

	private boolean loadVersion(String line) {
		boolean isNew = true;

		Version entity = seedDataService.getVersionFromCsvString(line);
		Version existing = masterDataService.findVersionByVersionNumber(entity.getVersionNumber());

		if (existing != null) 
		{
			existing.setVersionDescription(existing.getVersionDescription());
			masterDataService.saveVersion(existing);
			isNew = false;
		} 
		else 
		{
			masterDataService.saveVersion(entity);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadTppType(String line) {
		boolean isNew = true;

		TppType entity = seedDataService.getTppTypeFromCsvString(line);
		TppType existing = masterDataService.findTppTypeByTypeCode(entity.getTypeCode());

		if (existing != null) 
		{
			existing.setDescription(existing.getDescription());
			masterDataService.saveTppType(existing);
			isNew = false;
		} 
		else 
		{
			masterDataService.saveTppType(entity);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadDelimiter(String delimiterEntry)
	{
		boolean isNew = true;

		Delimiter delimiter = seedDataService.getDelimiterFromCsvString(delimiterEntry);
		Delimiter existing = masterDataService.findDelimiterByCode(delimiter.getDelimiter());

		if (existing != null) 
		{
			existing.setDescription(delimiter.getDescription());
			existing.setComposite(delimiter.isComposite());
			existing.setElement(delimiter.isElement());
			existing.setComposite(delimiter.isComposite());
			masterDataService.saveDelimiter(existing);
			isNew = false;
		} 
		else 
		{
			masterDataService.saveDelimiter(delimiter);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadACK(String ackEntry)
	{
		boolean isNew = true;

		Ack ack = seedDataService.getAckFromCsvString(ackEntry);
		Ack existing = masterDataService.findAckByCode(ack.getAckValue());

		if (existing != null) 
		{
			existing.setDescription(existing.getDescription());
			masterDataService.saveAck(existing);
			isNew = false;
		} 
		else 
		{
			masterDataService.saveAck(ack);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadDocument(String documentString)
	{
		boolean isNew = true;

		Document document = seedDataService.getDocumentFromCsvString(documentString);
		Document existing = masterDataService.findDocumentTypeByDocumentType(document.getDocumentType());

		if (existing != null) 
		{
			existing.setDocumentDescription(existing.getDocumentDescription());
			masterDataService.saveDocument(existing);
			isNew = false;
		} 
		else 
		{
			masterDataService.saveDocument(document);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadDirection(String line)
	{
		boolean isNew = true;

		Direction entity = seedDataService.getDirectionFromCsvString(line);
		Direction existing = masterDataService.findDirectionByName(entity.getDirectionCode());

		if (existing != null) 
		{
			existing.setDirectionDescription(existing.getDirectionDescription());
			masterDataService.saveDirection(existing);
			isNew = false;
		} 
		else 
		{
			masterDataService.saveDirection(entity);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadPartnerType(String line)
	{
		boolean isNew = true;

		PartnerType entity = seedDataService.getPartnerTypeFromCsvString(line);
		PartnerType existing = masterDataService.findPartnerTypeByTypeCode(entity.getTypeCode());

		if (existing != null) 
		{
			existing.setTypeDescription(entity.getTypeDescription());
			masterDataService.savePartnerType(existing);
			isNew = false;
		}
		else 
		{
			masterDataService.savePartnerType(entity);
			isNew = true;
		}

		return isNew;
	}

	private boolean loadProtocol(String line)
	{
		boolean isNew = true;

		Protocol entity = seedDataService.getProtocolFromCcvString(line);
		Protocol existing = masterDataService.findProtocolByProtocolType(entity.getProtocolType());

		if (existing != null) 
		{
			existing.setProtocolDescription(existing.getProtocolDescription());
			masterDataService.saveProtocol(existing);
			isNew = false;
		}
		else 
		{
			masterDataService.saveProtocol(entity);
			isNew = true;
		}

		return isNew;
	}

	//Added by Arindam Sikdar for Dynamic Data Load process
	private boolean loadContactDetail(String line) {
		boolean isNew = true;
		ContactDetail contactDetail = seedDataService.getContactDetailFromCsvString(line);
		partnerService.insertContactDetail(contactDetail);

		return isNew;
	}

	/*	//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
	private SeedDataInsertStatMsg loadPartner(String line) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		String validationStatus = validatePartnerString(line);
		if(validationStatus.length() == 0) {
			Partner partner = seedDataService.getPartnerFromXlsxString(line);
			Partner result = null;		
			if(partner != null && partner.getPartnerName() != AppConstants.errorCode01 && partner.getPartnerName() != AppConstants.errorCode10 ) {
				if(!partnerService.isPartnerExist(partner.getPartnerName())) {
					try {

						result = partnerService.createPartner(partner);
					} catch (TpiRepositoryException e) {
						// TODO Auto-generated catch block
						logger.error(AppConstants.errorCode02, e);
						returnFlag = 2;
						seedDataInsertStatMsg.setStatusMsg(AppConstants.errorCode02);
						e.printStackTrace();
					}
				}else {
					Partner existingPartner = partnerService.findPartnerByUniqueName(partner.getPartnerName());
					Set<ContactDetail> set = partner.getContactDetails();
					for (ContactDetail s : set) {
						partnerService.insertContactDetail(s);
						existingPartner.addContact(s);
					}
					existingPartner.setPartnerGroup(partner.getPartnerGroup());
					result = partnerService.updatePartner(existingPartner);
					returnFlag = 1;
				}
			} else {
				returnFlag = 2;
				if(partner != null && partner.getPartnerName() == AppConstants.errorCode01) {
					seedDataInsertStatMsg.setStatusMsg(AppConstants.errorCode01);
				}
				if(partner != null && partner.getPartnerName() == AppConstants.errorCode10) {
					seedDataInsertStatMsg.setStatusMsg(AppConstants.errorCode10);
				}
			}
		} else {
			returnFlag = 2;
			seedDataInsertStatMsg.setStatusMsg(validationStatus);
		}

		seedDataInsertStatMsg.setReturnFlag(returnFlag);
		return seedDataInsertStatMsg;
	}*/

	public String validatePartnerString(String partnerString) {
		String validationStatus = "";
		StringBuffer sb = new StringBuffer("");
		List<String> items = Arrays.asList(partnerString.split("\\s*,\\s*"));
		if(items.size() == 17) {
			if(items.get(0) == null || items.get(0).trim().length() == 0) {
				logger.error(AppConstants.errorCode04);
				sb.append(AppConstants.errorCode04);
			}
			if(items.get(1) == null || items.get(1).trim().length() == 0) {
				logger.error(AppConstants.errorCode05);
				sb.append(AppConstants.errorCode05);
			}
			if(items.get(2) == null || items.get(2).trim().length() == 0) {
				logger.error(AppConstants.errorCode06);
				sb.append(AppConstants.errorCode06);
			}
			if(items.get(3) == null || items.get(3).trim().length() == 0) {
				logger.error(AppConstants.errorCode07);
				sb.append(AppConstants.errorCode07);
			}
			if(items.get(14) == null || items.get(14).trim().length() == 0) {
				logger.error(AppConstants.errorCode08);
				sb.append(AppConstants.errorCode08);
			}
			if(items.get(16) == null || items.get(16).trim().length() == 0) {
				logger.error(AppConstants.errorCode09);
				sb.append(AppConstants.errorCode09);
			}

		}else {
			logger.error(AppConstants.errorCode03);
			sb.append(AppConstants.errorCode03);
		}

		validationStatus = sb.toString();
		//logger.debug("Validation Status: " + validationStatus);
		return validationStatus;
	}

	/*	//Added by Arindam Sikdar for Dynamic Data Load process - TPP Load
	private SeedDataInsertStatMsg loadTPP(String line) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		TPPReturnMsgBean tppReturnMsgBean = seedDataService.getTPPFromXlsxString(line);
		Tpp tpp = null;
		Tpp result = null;	
		if(tppReturnMsgBean.getTpp() == null) {
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg(tppReturnMsgBean.getReturnMsg());
		} else {
			seedDataInsertStatMsg.setReturnFlag(tppReturnMsgBean.getReturnFlag());
		}
		return seedDataInsertStatMsg;
	}*/

	//Added by Arindam Sikdar for Dynamic Data Load process - Compliance Map load
	private SeedDataInsertStatMsg loadComplianceMap(String line) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		ComplianceMapReturnMsgBean complianceMapReturnMsgBean = seedDataService.getComplainceMapFromXlsxString(line);
		if(complianceMapReturnMsgBean.getServiceType() == null) {
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg(complianceMapReturnMsgBean.getReturnMsg());
		} else {
			seedDataInsertStatMsg.setReturnFlag(complianceMapReturnMsgBean.getReturnFlag());
		}

		return seedDataInsertStatMsg;
	}

	public ComplianceMapReturnMsgBean getComplainceMapFromXlsxString(String cMapString) {

		ComplianceMapReturnMsgBean complianceMapReturnMsgBean = new ComplianceMapReturnMsgBean();
		complianceMapReturnMsgBean.setReturnMsg("");
		complianceMapReturnMsgBean.setReturnFlag(2);
		ServiceType serviceType = null;

		List<String> items = Arrays.asList(cMapString.split("\\s*,\\s*"));
		logger.debug("Invoked getComplainceMapFromXlsxString() method.");
		logger.debug("Column Size: " + items.size());


		if(items.size() == 3) {
			if(items.get(0) == null || items.get(0).trim().length() == 0) {
				complianceMapReturnMsgBean.setReturnMsg(AppConstants.errorCode39);
				logger.error(AppConstants.errorCode39);
				return complianceMapReturnMsgBean;
			}
			if(items.get(1) == null || items.get(1).trim().length() == 0) {
				complianceMapReturnMsgBean.setReturnMsg(AppConstants.errorCode42);
				logger.error(AppConstants.errorCode42);
				return complianceMapReturnMsgBean;
			}
			if(items.get(2) == null || items.get(2).trim().length() == 0) {
				complianceMapReturnMsgBean.setReturnMsg(AppConstants.errorCode43);
				logger.error(AppConstants.errorCode43);
				return complianceMapReturnMsgBean;
			}

			serviceType = serviceTypeRepository.findServiceTypeByBusinessServiceNameIgnoreCase(items.get(1));
			if(serviceType != null) {
				if(!serviceType.getServiceCategory().getName().equalsIgnoreCase(items.get(0).trim())) {
					complianceMapReturnMsgBean.setReturnMsg(AppConstants.errorCode40);
					logger.error(AppConstants.errorCode40);
					return complianceMapReturnMsgBean;
				}

				List<TpiMap> allMapsList = masterDataService.getAllMaps();
				boolean isExistingMap = false;
				TpiMap newMap = null;
				//TpiMap result = null;
				for(TpiMap tMap : allMapsList) {
					if(tMap.getMapName().equalsIgnoreCase(items.get(2).trim())) {
						isExistingMap = true;
						newMap = tMap;
						break;
					}
				}
				if(newMap == null) {
					newMap = new TpiMap();
					newMap.setMapDescription(items.get(2).trim());
					newMap.setMapName(items.get(2).trim());							

				}
				masterDataService.addMapToServiceType(serviceType.getId(), newMap);
				complianceMapReturnMsgBean.setServiceType(serviceType);
				complianceMapReturnMsgBean.setReturnFlag(0);
				return complianceMapReturnMsgBean;

			} else {
				complianceMapReturnMsgBean.setReturnMsg(AppConstants.errorCode41);
				logger.error(AppConstants.errorCode41);
				return complianceMapReturnMsgBean;
			}

		} else {
			complianceMapReturnMsgBean.setReturnMsg(AppConstants.errorCode03);
			logger.error(AppConstants.errorCode03);
		}

		if(complianceMapReturnMsgBean.getReturnFlag() == 2 && complianceMapReturnMsgBean.getReturnMsg().length() == 0) {
			complianceMapReturnMsgBean.setReturnMsg("Nothing to update.");
		}
		return complianceMapReturnMsgBean;
	}


	//Added by Pappu Prasad for Dynamic Data Load process - ABCIdMaintenance Load
	private SeedDataInsertStatMsg loadIDMaintenance(String line) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		//ServiceCategory serviceCategory;
		logger.debug("String line: " + line);
		ABCIDReturnMsgBean idmReturnMsgBean = seedDataService.getIDMFromXlsxString(line);
		logger.debug("String idmReturnMsgBean: " + idmReturnMsgBean);

		if(idmReturnMsgBean.getServiceCategory() == null) {
			//seedDataInsertStatMsg.setReturnFlag(1);
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg(idmReturnMsgBean.getReturnMsg());
		} else {
			seedDataInsertStatMsg.setReturnFlag(idmReturnMsgBean.getReturnFlag());
		}
		return seedDataInsertStatMsg;

	}

	public ABCIDReturnMsgBean updateServiceCategorywithLW(ServiceCategory serviceCategory, LightWellPartner lightWellPartner, List<String> items, ABCIDReturnMsgBean abcdReturnMsgBean) {
		lightWellPartner.setTestIsaID(items.get(1).trim());
		lightWellPartner.setTestIsaQualifier(items.get(2).trim());
		lightWellPartner.setTestGsId(items.get(3).trim());
		lightWellPartner.setProductionIsaID(items.get(4).trim());
		lightWellPartner.setProductionIsaQualifier(items.get(5).trim());
		lightWellPartner.setProductionGsId(items.get(6).trim());
		serviceCategory.addLightWellPartner(lightWellPartner);
		serviceCategory = serviceSubscriptionService.saveServiceCategory(serviceCategory);
		abcdReturnMsgBean.setServiceCategory(serviceCategory);
		abcdReturnMsgBean.setReturnFlag(1);
		return abcdReturnMsgBean;

	}
	//Added by Pappu Prasad for Dynamic Data Load process - TPP Load

	/*	ABCIDMaintenance Template Details
		----------------------------
		Col	Header
	  	0	Service Category	
		1	TEST ISA ID
		2	TEST ISA QUAL	
		3	TEST GS ID	
		4	PROD ISA ID
		5   PROD ISA QUAL
		6   PROD GS ID
	 */
	@Override
	public ABCIDReturnMsgBean getIDMFromXlsxString(String idmString1) {
		ABCIDReturnMsgBean abcdReturnMsgBean = new ABCIDReturnMsgBean();
		abcdReturnMsgBean.setReturnMsg("");
		abcdReturnMsgBean.setReturnFlag(2);
		//ServiceSubscriptionService ss = null;

		List<String> items = Arrays.asList(idmString1.split("\\s*,\\s*",-1));
		//List<String> items = Arrays.asList(tppString.split(","));	
		logger.debug("Inside getIDMFromXlsxString() method.");
		logger.debug("Column Size: " + items.size());
		if(items.size() == 8) {
			if(items.get(0) == null || items.get(0).trim().length() == 0) {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode39);
				logger.error(AppConstants.errorCode39);
				return abcdReturnMsgBean;
			} 

			if(items.get(1) == null || items.get(1).trim().length() == 0) {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode14);
				logger.error(AppConstants.errorCode14);
				return abcdReturnMsgBean;
			} else if(items.get(2) == null || items.get(2).trim().length() == 0)  {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode15);
				logger.error(AppConstants.errorCode15);
				return abcdReturnMsgBean;
			} else if(items.get(3) == null || items.get(3).trim().length() == 0)  {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode16);
				logger.error(AppConstants.errorCode16);
				return abcdReturnMsgBean;
			} else if(items.get(4) == null || items.get(4).trim().length() == 0)  {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode17);
				logger.error(AppConstants.errorCode17);
				return abcdReturnMsgBean;
			} else if(items.get(5) == null || items.get(5).trim().length() == 0)  {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode18);
				logger.error(AppConstants.errorCode18);
				return abcdReturnMsgBean;
			} else if(items.get(6) == null || items.get(6).trim().length() == 0)  {
				abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode19);
				logger.error(AppConstants.errorCode19);
				return abcdReturnMsgBean;
			}
		}
		ServiceCategory serviceCategory = serviceSubscriptionService.findServiceCategoryByNameIgnoreCase(items.get(0).trim());
		if(serviceCategory == null) {
			abcdReturnMsgBean.setReturnMsg(AppConstants.errorCode40);
			logger.error(AppConstants.errorCode40);
			return abcdReturnMsgBean;
		}
		LightWellPartner lightWellPartner = null; //new LightWellPartner();
		lightWellPartner = serviceSubscriptionService.findLightWellPartner(items.get(1),items.get(2),items.get(3),items.get(4),items.get(5),items.get(6), true);
		logger.debug("LightWellPartner object : " + lightWellPartner);

		if(lightWellPartner == null) {

			lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(items.get(1).trim(),null,null,null,null);
			if(lightWellPartner != null) {	
				logger.debug("getIDMFromXlsxString() :: Updating existing LW Partner Object based on TESTIsaID.");
				return updateServiceCategorywithLW(serviceCategory, lightWellPartner, items, abcdReturnMsgBean);
			} 	
			lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,items.get(3).trim(),null,null,null);
			if(lightWellPartner != null) {	
				logger.debug("getIDMFromXlsxString() :: Updating existing LW Partner Object based on TESTGSID.");
				return updateServiceCategorywithLW(serviceCategory, lightWellPartner, items, abcdReturnMsgBean);
			} 
			lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,items.get(4).trim(),null,null);
			if(lightWellPartner != null) {	
				logger.debug("getIDMFromXlsxString() :: Updating existing LW Partner Object based on PRODIsaID.");
				return updateServiceCategorywithLW(serviceCategory, lightWellPartner, items, abcdReturnMsgBean);
			} 
			lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,items.get(6).trim(),null);
			if(lightWellPartner != null) {	
				logger.debug("getIDMFromXlsxString() :: Updating existing LW Partner Object based on PRODGSID.");
				return updateServiceCategorywithLW(serviceCategory, lightWellPartner, items, abcdReturnMsgBean);
			} 

			LightWellPartner lwp = new LightWellPartner();
			lwp.setActive(true);
			lwp.setProductionGsId(items.get(6).trim());
			lwp.setProductionIsaID(items.get(4).trim());
			lwp.setProductionIsaQualifier(items.get(5).trim());
			lwp.setTestGsId(items.get(3).trim());
			lwp.setTestIsaID(items.get(1).trim());
			lwp.setTestIsaQualifier(items.get(2).trim());
			serviceCategory.addLightWellPartner(lwp);
			logger.debug("getLightWellPartners: " + serviceCategory.getLightWellPartners().toString());
			serviceCategory = serviceSubscriptionService.saveServiceCategory(serviceCategory);
			abcdReturnMsgBean.setServiceCategory(serviceCategory);
			abcdReturnMsgBean.setReturnFlag(0);
		}
		else
		{					
			logger.debug("getLightWellPartners ---   : " + serviceCategory.getLightWellPartners().toString());

			serviceCategory.addLightWellPartner(lightWellPartner);						
			abcdReturnMsgBean.setServiceCategory(serviceSubscriptionService.saveServiceCategory(serviceCategory));
			abcdReturnMsgBean.setReturnFlag(1);
			return abcdReturnMsgBean;				
		}

		if(abcdReturnMsgBean.getReturnFlag() == 2 && abcdReturnMsgBean.getReturnMsg().length() == 0) {
			abcdReturnMsgBean.setReturnMsg("Nothing to update.");
		}
		return abcdReturnMsgBean;
	}	



	//Added by Arindam Sikdar for Dynamic Data Load process - TPP Load
	private String validateTPPString(String tppString) {
		String validationStatus = "";
		StringBuffer sb = new StringBuffer("");
		List<String> items = Arrays.asList(tppString.split("\\s*,\\s*", -1));	
		//List<String> items = Arrays.asList(tppString.split(","));	
		if(items.get(8) == null || items.get(8).trim().length() == 0) {
			sb.append(AppConstants.errorCode13);
		} else if(items.get(13) == null || items.get(13).trim().length() == 0) {
			sb.append(AppConstants.errorCode21);
		} else if(items.get(14) == null || items.get(14).trim().length() == 0) {
			sb.append(AppConstants.errorCode22);
		} else if(items.get(15) == null || items.get(15).trim().length() == 0) {
			sb.append(AppConstants.errorCode23);
		} else if(items.get(16) == null || items.get(16).trim().length() == 0) {
			sb.append(AppConstants.errorCode24);
		} else if(items.get(17) == null || items.get(17).trim().length() == 0) {
			sb.append(AppConstants.errorCode25);
		} else if(items.get(18) == null || items.get(18).trim().length() == 0) {
			sb.append(AppConstants.errorCode26);
		} else if(items.get(20) == null || items.get(20).trim().length() == 0) {
			sb.append(AppConstants.errorCode27);
		} else if(items.get(31) == null || items.get(31).trim().length() == 0) {
			sb.append(AppConstants.errorCode08);
		} else if(items.get(33) == null || items.get(33).trim().length() == 0) {
			sb.append(AppConstants.errorCode09);
		} 



		validationStatus = sb.toString();
		//logger.debug("Validation Status: " + validationStatus);
		return validationStatus;
	}



	//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
	@Override
	public Partner getPartnerFromXlsxString(String partnerString) {
		Partner partner = null; 
		PartnerGroup partnerGroup = null;
		ContactDetail contactDetail = null;
		//Document doc = null;
		List<String> items = Arrays.asList(partnerString.split("\\s*,\\s*"));
		//logger.debug("Total column number in Partner sheet: " + items.size());
		if(items.size() == 17) {
			partner = new Partner();

			partner.setPartnerName(items.get(0));

			if(partnerGroupService.isPartnerGroupExists(items.get(1), items.get(2))) {
				partnerGroup = partnerGroupService.getPartnerGroupByGroupSubGroupName(items.get(1), items.get(2));
				partner.setPartnerGroup(partnerGroup);
				//contactDetail = seedDataService.getContactDetailFromCsvString(partnerString);
				contactDetail = seedDataService.getContactDetailFromCsvString(items.get(3)+","+items.get(4)+","+items.get(5)+","+items.get(6)+","+items.get(7)+","+items.get(8)+","+items.get(9)+","+items.get(10)+","+items.get(11)+","+items.get(12)+","+items.get(13)+","+items.get(14)+","+items.get(15)+","+items.get(16)+",");
				if(contactDetail.getContactName() == AppConstants.errorCode10) {
					//Raise error
					logger.error(AppConstants.errorCode10);
					partner.setPartnerName(AppConstants.errorCode10);
				} else {
					partner.addContact(contactDetail);
				}
			} else {
				//Raise error
				logger.error(AppConstants.errorCode01);
				partner.setPartnerName(AppConstants.errorCode01);
			}			


		}				


		return partner;
	}


	//Added by Arindam Sikdar for Dynamic Data Load process
	@Override
	public ContactDetail getContactDetailFromCsvString(String contactString) {
		ContactDetail contactDetail = null;
		Document doc = null;
		List<String> items = Arrays.asList(contactString.split("\\s*,\\s*"));
		//logger.debug("Total column number: " + items.size());
		if(items.size() == 14) {
			contactDetail = new ContactDetail();

			contactDetail.setContactName(items.get(0));
			contactDetail.setContactTitle(items.get(1));
			contactDetail.setBusinessPhoneCountry(items.get(2));
			contactDetail.setBusinessPhone(items.get(3));
			contactDetail.setBusinessPhoneExt(items.get(4));
			contactDetail.setMobilePhoneCountry(items.get(5));
			contactDetail.setMobilePhone(items.get(6));
			contactDetail.setMobilePhoneExt(items.get(7));
			contactDetail.setPersonalPhoneCountry(items.get(8));
			contactDetail.setPersonalPhone(items.get(9));
			contactDetail.setPersonalPhoneExt(items.get(10));
			contactDetail.setContactEmail(items.get(11));
			contactDetail.setContactEmail2(items.get(12));

			doc = masterDataService.findDocumentTypeByDocumentType(items.get(13));
			if(doc != null) {
				contactDetail.setTransactionType(doc);
				//logger.debug("DOC object is not null.");
			}else {
				contactDetail.setContactName(AppConstants.errorCode10);
				logger.error(AppConstants.errorCode10);
			}

		}


		return contactDetail;

	}

	@Override
	public PartnerGroup getPartnerGroupFromCsvString(String text) {
		PartnerGroup entity = null;
		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));
		if (items.size() == 2) {
			entity = new PartnerGroup();
			entity.setGroupName(items.get(0));
			entity.setSubGroupName(items.get(1));
		}
		return entity;
	}

	@Override
	public ServiceType getServiceTypeFromCsvString(String text) {
		ServiceType entity = null;
		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));
		if (items.size() == 7) {
			entity = new ServiceType();
			try 
			{

				ServiceCategory serviceCategory = serviceSubscriptionService.findServiceCategoryByNameIgnoreCase(items.get(2));

				if (serviceCategory==null)
				{
					throw new Exception ("Service Category with Name of " + items.get(2) + " not found.");
				}


				String directionName = items.get(4);
				String documentType = items.get(5);
				Integer versionNumber = new Integer(items.get(6));
				String companyName  = items.get(0);
				String clientType = items.get(1);

				Document document = masterDataService.findDocumentTypeByDocumentType(documentType);

				if (document==null)
				{
					throw new Exception ("Document of Type " + documentType + " not found.");
				}

				Direction direction = masterDataService.findDirectionByName(directionName);

				if (direction==null)
				{
					throw new Exception ("Direction " + directionName + " not found.");
				}

				Version version = masterDataService.findVersionByVersionNumber(versionNumber);

				if (version==null)
				{
					throw new Exception ("Version " + versionNumber + " not found.");
				}

				if (companyName.equalsIgnoreCase("ABSG"))
				{
					entity.setCompany(CompanyEnum.ABSG);
				}
				else
				{
					entity.setCompany(CompanyEnum.ABSG);
				}

				if (clientType.equalsIgnoreCase("Supplier"))
				{
					entity.setPartnerCategory(PartnerCategoryEnum.Supplier);
				}
				else
				{
					entity.setPartnerCategory(PartnerCategoryEnum.Customer);
				}
				entity.setBusinessServiceName(items.get(3));
				entity.setDirection(direction);
				entity.setDocument(document);
				entity.setServiceCategory(serviceCategory);

			}
			catch (NumberFormatException ex)
			{
				logger.error("Numbers are expected for Service Category ID, Document and Version : " + text);
			}
			catch (Exception ex)
			{
				logger.error(ex.getMessage());
			}

		}
		return entity;
	}

	private boolean loadPartnerGroup(String text)
	{
		boolean isNew = false;

		PartnerGroup entity = seedDataService.getPartnerGroupFromCsvString(text);
		if(!partnerGroupService.isPartnerGroupExists(entity.getGroupName(), entity.getSubGroupName()))
		{
			partnerGroupService.savePartnerGroup(entity);
			isNew=true;
		}

		return isNew;
	}

	private boolean loadServiceType(String text)
	{
		boolean isNew = true;

		ServiceType entity = seedDataService.getServiceTypeFromCsvString(text);
		serviceSubscriptionService.saveServiceType(entity);
		return isNew;
	}

	private boolean loadServiceCategoryLightWellPartner(String text)
	{
		boolean isNew = true;

		LightWellPartner lwPartner = seedDataService.getLightWellPartnerFromCsvString(text);
		String serviceCategoryName = text.substring(0,text.indexOf(","));
		ServiceCategory serviceCategory = serviceSubscriptionService.findServiceCategoryByNameIgnoreCase(serviceCategoryName.trim());

		if (serviceCategory==null)
		{
			serviceCategory = new ServiceCategory();
			serviceCategory.setName(serviceCategoryName);
		}

		serviceCategory.addLightWellPartner(lwPartner);
		serviceSubscriptionService.saveServiceCategory(serviceCategory);			


		return isNew;
	}

	@Override
	public LightWellPartner getLightWellPartnerFromCsvString(String text) {
		LightWellPartner lightWellPartner = null;
		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));
		if (items.size() == 10) {
			lightWellPartner = new LightWellPartner();
			lightWellPartner.setTestIsaID(items.get(1));
			lightWellPartner.setTestIsaQualifier(items.get(2));
			lightWellPartner.setTestGsId(items.get(3));
			lightWellPartner.setProductionIsaID(items.get(4));
			lightWellPartner.setProductionIsaQualifier(items.get(5));
			lightWellPartner.setProductionGsId(items.get(6));
			lightWellPartner.setOrganizationName(items.get(7));
			lightWellPartner.setActive(true);
			lightWellPartner.setNotes(items.get(9));

		}
		return lightWellPartner;
	}

	@Override
	public TpiMap getMapFromCsvString(String text) {

		TpiMap result = null;


		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));

		try
		{


			if (items.size() == 2 || items.size() == 1)
			{
				result = new TpiMap();
				String mapName = items.get(0);
				result.setMapName(mapName);

				if (items.size()==2)
				{
					result.setMapDescription(items.get(1));
				}

			}
		}
		catch (Exception ex)
		{
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	public ServiceType loadMapsForServiceCategory(String text) {
		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));

		ServiceType result = null;

		try
		{
			if (items.size() == 2)
			{
				ServiceType serviceType = masterDataService.findServiceTypeByName(items.get(0));
				String mapName = items.get(1);

				if (serviceType!=null)
				{					
					TpiMap existingMap = masterDataService.findMapByName(mapName);
					if (existingMap!=null)
					{
						serviceType.addMap(existingMap);
						result = serviceType;
						serviceTypeRepository.save(serviceType);
					}
				}

			}
		}
		catch (Exception ex)
		{
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	public SdBusinessUnit getSdBusinessUnitFromString(String text) {
		SdBusinessUnit sdBusinessUnit = null;
		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));
		if (items.size() == 2)
		{
			sdBusinessUnit = new SdBusinessUnit();
			sdBusinessUnit.setName(items.get(0));
			SdBusinessSubUnit subUnit = new SdBusinessSubUnit();
			subUnit.setSubUnitName(items.get(1));
			sdBusinessUnit.addSubUnit(subUnit);

		}
		return sdBusinessUnit;
	}

	@Override
	public SdServiceType getSdServiceTypeFromString(String text) {
		SdServiceType sdServiceType = null;
		List<String> items = Arrays.asList(text.split("\\s*,\\s*"));
		if (items.size() == 1) 
		{
			sdServiceType = new SdServiceType();
			sdServiceType.setName(items.get(0));
		}
		else if (items.size() == 2)
		{
			sdServiceType = new SdServiceType();
			sdServiceType.setName(items.get(0));
			sdServiceType.setDescription(items.get(0));
		}
		return sdServiceType;
	}

	protected boolean loadSdServiceTypeFromString(String text)
	{
		boolean result = false;
		SdServiceType sdServiceType = this.getSdServiceTypeFromString(text);
		if (sdServiceType!=null)
		{
			SdServiceType existing = sdMasterDataService.findServiceTypeByName(sdServiceType.getName());

			if (existing==null)
			{
				sdMasterDataService.saveServiceType(sdServiceType);
				result = true;
			}
			else
			{
				existing.setDescription(sdServiceType.getDescription());
				sdMasterDataService.saveServiceType(existing);
				result = false;
			}
		}
		return result;
	}

	@Transactional
	protected boolean loadSdBusinessUntiFromString(String text)
	{
		boolean result = false;
		SdBusinessUnit sdBusinessUnit = getSdBusinessUnitFromString(text);

		if (sdBusinessUnit!=null)
		{
			SdBusinessUnit existing = sdMasterDataService.findSdBusinessUnitByName(sdBusinessUnit.getName());

			if (existing==null)
			{
				sdMasterDataService.saveSdBusinessUnit(sdBusinessUnit);
				result = true;
			}
			else
			{
				boolean found = false;
				for (SdBusinessSubUnit bs: existing.getSubUnits())
				{
					if (bs.getSubUnitName().equalsIgnoreCase(sdBusinessUnit.getSubUnits().iterator().next().getSubUnitName()))
					{
						found = true;
						break;
					}
				}

				if (!found)
				{
					existing.addSubUnit(sdBusinessUnit.getSubUnits().iterator().next());
					sdMasterDataService.saveSdBusinessUnit(existing);
					result = false;
				}
			}

		}

		return result;
	}
	@Transactional
	protected boolean loadServiceCategoryDefFromText(String line) throws TpiValidationException
	{
		boolean result = false;

		List<String> items = Arrays.asList(line.split("\\s*,\\s*"));

		this.validateSdBusinessServiceCategoryInput(items);

		String serviceKey = items.get(0).trim();
		String serviceCategory = items.get(1).trim();
		int categoryId = Integer.valueOf(items.get(2));
		String serviceLevel = items.get(3).trim();
		String serviceLevelId = items.get(4).trim();
		String businessUnit = items.get(5).trim();
		String businessSubUnit = items.get(6).trim();
		String servicePreamble = items.get(7).trim();
		String transactionType = items.get(8).trim();
		String direction = items.get(9).trim();
		String serviceType = items.get(10).trim();
		String interCo = items.get(11).trim();
		String serviceDescription = items.get(12).trim();
		int transactionCode = 0;

		SdBusinessService businessService = null;

		businessService = sdServiceCategoryService.findBusinessServiceByServiceKey(serviceKey, SdBusinessService.class);

		if (businessService==null)
		{
			businessService = new SdBusinessService();
			businessService.setServiceKey(serviceKey);
		}


		Document doc = masterDataService.findDocumentTypeByDocumentType(transactionType);	

		if (doc!=null)
		{
			businessService.setDocument(doc);
		}
		else
		{
			throw new TpiValidationException("Transaction " + transactionType + " not found. Add new Transaction code before retrying again");
		}

		if (direction.equalsIgnoreCase("I"))
		{
			direction = "INBOUND";
		}
		else if(direction.equalsIgnoreCase("O"))
		{
			direction = "OUTBOUND";
		}

		Direction directionType = masterDataService.findDirectionByName(direction);

		if (directionType==null)
		{
			throw new TpiValidationException("Direction " + direction + " not found. Add new Direction code before retrying again");
		}

		businessService.setDirection(directionType);
		businessService.setInterCoSendToBU(interCo);
		businessService.setServiceLevel(serviceLevel);
		businessService.setServiceLevelId(serviceLevelId);
		businessService.setServicePreamble(servicePreamble);
		businessService.setServiceDescription(serviceDescription);


		SdServiceCategoryDef serviceCategoryDef = null;		

		serviceCategoryDef = sdServiceCategoryService.findSdServiceCategoryByName(serviceCategory, SdServiceCategoryDef.class);

		if (serviceCategoryDef==null)
		{
			serviceCategoryDef = new SdServiceCategoryDef();
			ServiceCategory sc = serviceSubscriptionService.findServiceCategoryByNameIgnoreCase(serviceCategory);
			serviceCategoryDef.setCategoryID(categoryId);

			if (sc==null)
			{
				sc = new ServiceCategory();
				sc.setName(serviceCategory);
				sc = serviceSubscriptionService.saveServiceCategory(sc);				
			}

			serviceCategoryDef.setServiceCategory(sc);

			SdBusinessUnit bu = sdMasterDataService.findSdBusinessUnitByName(businessUnit);

			if (bu==null)
			{
				bu = new SdBusinessUnit();
				bu.setName(businessUnit);
				SdBusinessSubUnit bsu = new SdBusinessSubUnit();
				bsu.setSubUnitName(businessSubUnit);
				bu.addSubUnit(bsu);
				bu = sdMasterDataService.saveSdBusinessUnit(bu);
				serviceCategoryDef.setBusinessUnit(bu);
				serviceCategoryDef.setBusinessSubUnit(bsu);
			}
			else
			{
				if (bu.getSubUnits()!=null)
				{
					for (SdBusinessSubUnit bsu: bu.getSubUnits())
						if (bsu.getSubUnitName().equalsIgnoreCase(businessSubUnit))
						{	
							serviceCategoryDef.setBusinessSubUnit(bsu);
							break;
						}
					if (serviceCategoryDef.getBusinessSubUnit()==null)
					{
						SdBusinessSubUnit bsu = new SdBusinessSubUnit();
						bsu.setSubUnitName(businessSubUnit);
						bu.addSubUnit(bsu);
						bu = sdMasterDataService.saveSdBusinessUnit(bu);
						serviceCategoryDef.setBusinessUnit(bu);
						for (SdBusinessSubUnit newBsu: bu.getSubUnits())
						{
							if (newBsu.getSubUnitName().equalsIgnoreCase(businessSubUnit))
							{
								serviceCategoryDef.setBusinessSubUnit(newBsu);
								break;
							}
						}

					}
					else
					{
						serviceCategoryDef.setBusinessUnit(bu);
					}
				}

			}
			serviceCategoryDef.setPartnerSubscription(SdYesNo.Y);
			sdServiceCategoryService.saveSdServiceCategoryDef(serviceCategoryDef);
		}

		businessService.setServiceCategory(serviceCategoryDef);

		SdServiceType serviceTypeEntity = sdMasterDataService.findServiceTypeByName(serviceType);
		if (serviceTypeEntity==null)
		{
			serviceTypeEntity = new SdServiceType();
			serviceTypeEntity.setName(serviceType);
			serviceTypeEntity.setDescription(serviceType);
			sdMasterDataService.saveServiceType(serviceTypeEntity);
		}

		businessService.setServiceType(serviceTypeEntity);
		sdServiceCategoryService.saveSdBusinessService(businessService);


		//save ServiceType
		ServiceType tpiServiceType = null;
		tpiServiceType = serviceTypeRepository.findServiceTypeByBusinessServiceNameIgnoreCase(serviceDescription);

		if (tpiServiceType==null)
		{
			tpiServiceType = new ServiceType();
			tpiServiceType.setBusinessServiceName(serviceDescription);
		}

		tpiServiceType.setDirection(directionType);
		try
		{
			tpiServiceType.setCompany(Enum.valueOf(CompanyEnum.class, serviceLevel));
		}
		catch (IllegalArgumentException ex)
		{
			throw new TpiValidationException("Company " + serviceLevel + " is not valid");
		}
		tpiServiceType.setDocument(doc);
		tpiServiceType.setServiceCategory(serviceCategoryDef.getServiceCategory());

		serviceTypeRepository.save(tpiServiceType);

		return result;
	}

	private void validateSdBusinessServiceCategoryInput(List<String> values) throws TpiValidationException
	{
		if (values.size()!=13) 
		{
			throw new TpiValidationException("SdServiceCategory data file expects 13 values");
		}

		if (values.get(0)==null || values.get(0).isEmpty())
		{
			throw new TpiValidationException("SdServiceCategory: Service Key cannot be empty");
		}

		if (values.get(1)==null || values.get(1).isEmpty())
		{
			throw new TpiValidationException("SdServiceCategory: Service Category cannot be empty");
		}

		if (values.get(7)==null || values.get(7).isEmpty())
		{
			throw new TpiValidationException("SdServiceCategory: Service Preamble cannot be empty");
		}

		if (values.get(8)==null || values.get(8).isEmpty())
		{
			throw new TpiValidationException("SdServiceCategory: Transaction cannot be empty");
		}

		if (values.get(9)==null || values.get(9).isEmpty())
		{
			throw new TpiValidationException("SdServiceCategory: Direction cannot be empty");
		}

		if (values.get(10)==null || values.get(10).isEmpty())
		{
			throw new TpiValidationException("SdServiceCategory: Service Type cannot be empty");
		}

	}

	//Added by Pappu Prasad for Dynamic Data Load process - ServiceSubscription Load
	private SeedDataInsertStatMsg loadServiceSubscription(String line) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		logger.debug("String line: " + line);
		SSReturnMsgBean ssReturnMsgBean = seedDataService.getSSFromXlsxString(line);
		logger.debug("String ssReturnMsgBean: " + ssReturnMsgBean);

		if(ssReturnMsgBean.getServiceSubscription() == null) {
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg(ssReturnMsgBean.getReturnMsg());
		} else {
			seedDataInsertStatMsg.setReturnFlag(ssReturnMsgBean.getReturnFlag());
		}
		return seedDataInsertStatMsg;

	}

	//Added by Pappu Prasad for Dynamic Data Load process - ServiceSubscription Load

	/*	SERVICESUBSCRIPTION Template Details
			----------------------------
			Col	Header
			0: Partner Name	
			1: Segment Delimiter	
			2: Element Delimiter	
			3: Composite Delimiter	
			4: Repeat Delimiter	
			5: Partner Test ISA ID	
			6: Partner Test ISA Qual	
			7: Partner Test GS ID	
			8: Partner Production ISA ID	
			9: Partner Production ISA Qual	
			10: Partner Production GS ID	
			11: ABC GS Production ID	
			12: ISA Version	
			13: GS Version	
			14: Protocol	
			15: Ack Required?	
			16: Ack Period	
			17: ST Control Number	
			18: ISA Control Number	
			19: ST AcceptorLookUpAlias	 
			20: Compliance Required?	
			21: Complaince Map Name:	
			22: 3PP Name	
			23: Service Category	
			24: Service Category Request ID	
			25: Service Category Comments	
			26: Business Service	
			27: BusinessService Request ID	
			28: BusinessService Comments
	 */
	@Override
	public SSReturnMsgBean getSSFromXlsxString(String ssString)  {
		// TODO Auto-generated method stub
		SSReturnMsgBean ssReturnMsgBean = new SSReturnMsgBean();
		ssReturnMsgBean.setReturnMsg("");
		ssReturnMsgBean.setReturnFlag(2);
		//ServiceSubscriptionService ss = null;

		List<String> items = Arrays.asList(ssString.split("\\s*,\\s*",-1));
		//List<String> items = Arrays.asList(tppString.split(","));	
		logger.debug("Inside getSSFromXlsxString() method.");
		logger.debug("Column Size: " + items.size());
		if(items.size() == 32) {
			if(items.get(0) == null || items.get(0).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode04);
				logger.error(AppConstants.errorCode04);
				return ssReturnMsgBean;
			} 

			if(items.get(23) == null || items.get(23).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode39);
				logger.error(AppConstants.errorCode39);
				return ssReturnMsgBean;
			} 

			if(items.get(1) == null || items.get(1).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode48);
				logger.error(AppConstants.errorCode48);
				return ssReturnMsgBean;
			} 
			if(items.get(2) == null || items.get(2).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode49);
				logger.error(AppConstants.errorCode49);
				return ssReturnMsgBean;
			} 
			if(items.get(3) == null || items.get(3).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode50);
				logger.error(AppConstants.errorCode50);
				return ssReturnMsgBean;
			} 

			if(items.get(5) == null || items.get(5).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode14);
				logger.error(AppConstants.errorCode14);
				return ssReturnMsgBean;
			} else if(items.get(6) == null || items.get(6).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode15);
				logger.error(AppConstants.errorCode15);
				return ssReturnMsgBean;
			} else if(items.get(7) == null || items.get(7).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode16);
				logger.error(AppConstants.errorCode16);
				return ssReturnMsgBean;
			} else if(items.get(8) == null || items.get(8).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode17);
				logger.error(AppConstants.errorCode17);
				return ssReturnMsgBean;
			} else if(items.get(9) == null || items.get(9).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode18);
				logger.error(AppConstants.errorCode18);
				return ssReturnMsgBean;
			} else if(items.get(10) == null || items.get(10).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode19);
				logger.error(AppConstants.errorCode19);
				return ssReturnMsgBean;
			}
			if(items.get(24) == null || items.get(24).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode47);
				logger.error(AppConstants.errorCode47);
				return ssReturnMsgBean;
			} 
			if(items.get(26) == null || items.get(26).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode53);
				logger.error(AppConstants.errorCode53);
				return ssReturnMsgBean;
			} else if(items.get(11) == null || items.get(11).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode54);
				logger.error(AppConstants.errorCode54);
				return ssReturnMsgBean;
			} else if(items.get(12) == null || items.get(12).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode55);
				logger.error(AppConstants.errorCode55);
				return ssReturnMsgBean;
			} else if(items.get(14) == null || items.get(14).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode44);
				logger.error(AppConstants.errorCode44);
				return ssReturnMsgBean;
			} else if(items.get(15) == null || items.get(15).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode45);
				logger.error(AppConstants.errorCode45);
				return ssReturnMsgBean;
			} 
			if(items.get(20) == null || items.get(20).trim().length() == 0)  {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode46);
				logger.error(AppConstants.errorCode46);
				return ssReturnMsgBean;
			}
			/*if(items.get(21) == null || items.get(21).trim().length() == 0) {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode51);
				logger.error(AppConstants.errorCode51);
				return ssReturnMsgBean;
			}*/
			Partner partner = partnerRepository.findFirstByPartnerNameIgnoreCase(items.get(0).trim());

			if(partnerService.isPartnerExist(items.get(0).trim()))
			{
				logger.debug("Partner Name  :" + partner.getPartnerName());
				ServiceCategory scl = serviceSubscriptionService.findServiceCategoryByNameIgnoreCase(items.get(23).trim());
				if(scl == null) {
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode40);
					logger.error(AppConstants.errorCode40);
					return ssReturnMsgBean;
				}
				logger.debug("ServiceCategory is: "+scl.getName());

				Collection<Long> subscriptionIds = serviceSubscriptionService.findServiceSubscriptionByPartnerAndServiceCategory(partner.getId(), scl.getId());
				/*if(subscriptionIds !=null && subscriptionIds.size() >0)
				{

					logger.debug("subscriptionId  :"+subscriptionIds.iterator().next());
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode52);
					logger.error(AppConstants.errorCode52);
					return ssReturnMsgBean;	
					// do nothing or update subscription object


				}else	
				{	*/
				// Create subscription object to insert subscription data load
				if(items.get(4) != null && items.get(4).trim().length() != 0) {
					if(items.get(1) != null&& items.get(1).trim().length() != 0 && items.get(2) != null&& items.get(2).trim().length() != 0 && items.get(3) != null&& items.get(3).trim().length() != 0) {
						if((items.get(1).trim().equals(items.get(2).trim())) || (items.get(1).trim().equals(items.get(3).trim())) || (items.get(1).trim().equals(items.get(4).trim())) ) {
							ssReturnMsgBean.setReturnMsg(AppConstants.errorCode29);
							return ssReturnMsgBean;
						} else if((items.get(2).trim().equals(items.get(3).trim())) || (items.get(2).trim().equals(items.get(4).trim()))) {
							ssReturnMsgBean.setReturnMsg(AppConstants.errorCode29);
							return ssReturnMsgBean;
						} else if((items.get(3).trim().equals(items.get(4).trim()))) {
							ssReturnMsgBean.setReturnMsg(AppConstants.errorCode29);
							return ssReturnMsgBean;
						}

					} 
				} else {
					if(items.get(1) != null && items.get(1).trim().length() != 0 && items.get(2) != null && items.get(2).trim().length() != 0 && items.get(3) != null && items.get(3).trim().length() != 0) {
						if((items.get(1).trim().equals(items.get(2).trim())) || (items.get(1).trim().equals(items.get(3).trim())) || (items.get(2).trim().equals(items.get(3).trim())) ) {
							ssReturnMsgBean.setReturnMsg(AppConstants.errorCode29);
							return ssReturnMsgBean;
						}

					} 
				}



				LightWellPartner lightWellPartner = serviceSubscriptionService.findLightWellPartner(items.get(5),items.get(6),items.get(7),items.get(8),items.get(9),items.get(10), true);
				logger.debug("LightWellPartner object------- : " + lightWellPartner);

				if(lightWellPartner == null) {
					lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(items.get(5).trim(),null,null,null,null);
					if(lightWellPartner == null) {
						lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,items.get(7).trim(),null,null,null);
						if(lightWellPartner == null) {
							lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,items.get(8).trim(),null,null);
							if(lightWellPartner == null) {
								lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,items.get(10).trim(),null);
							}
						}
					}			
				}

				ServiceSubscription serviceSubscription = null;

				if(subscriptionIds !=null && subscriptionIds.size() >0) {
					for(Long ssId : subscriptionIds) {
						serviceSubscription = serviceSubscriptionService.findServiceSubscriptionById(ssId);
						break;
					}
				} else {				
					serviceSubscription = new ServiceSubscription();
				}
				serviceSubscription.setPartner(partner);

				Tpp tpp = tppService.findByNameFullStringMatchIgnoreCase(items.get(22).trim());

				if(items.get(22) != null && items.get(22).trim().length() > 0 && tpp == null) {
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode60);
					logger.error(AppConstants.errorCode60);
					return ssReturnMsgBean;	
				}

				//tpp.setLightWellPartner(lightWellPartner);
				//tpp.setName(items.get(22).trim());
				//serviceSubscription.setServiceCategory(scl);

				com.abc.tpi.model.service.Service service = new com.abc.tpi.model.service.Service();

				service.setTpp(tpp);
				Delimiter del1;
				Delimiter del2;
				Delimiter del3;
				Delimiter del4;
				if(tpp == null) {
					del1 = masterDataService.findDelimiterByCode(items.get(1).trim());
					del2 = masterDataService.findDelimiterByCode(items.get(2).trim());
					del3 = masterDataService.findDelimiterByCode(items.get(3).trim());
					del4 = masterDataService.findDelimiterByCode(items.get(4).trim());
				} else {
					del1 = tpp.getSegmentDelimiter();
					del2 = tpp.getElementDelimiter();
					del3 = tpp.getCompositeElementDelimiter();
					del4 = tpp.getRepeatDelimiter();
				}

				service.setCompositeElementDelimiter(del3);
				service.setElementDelimiter(del2);
				service.setSegmentDelimiter(del1);
				service.setRepeatDelimiter(del4);
				service.setSrId(items.get(24).trim());
				service.setNotes(items.get(25).trim());
				if(tpp != null && tpp.getType().getTypeCode() == Short.valueOf("1")) {

					//scl.addLightWellPartner(tpp.getLightWellPartner());
					logger.debug("LightWellPartner object------- : " + tpp.getLightWellPartner().getId());
					service.setLightWellPartner(tpp.getLightWellPartner());

				} else if(lightWellPartner == null) {

					LightWellPartner lwp = new LightWellPartner();
					lwp.setActive(true);
					lwp.setProductionGsId(items.get(10).trim());
					lwp.setProductionIsaID(items.get(8).trim());
					lwp.setProductionIsaQualifier(items.get(9).trim());
					lwp.setTestGsId(items.get(7).trim());
					lwp.setTestIsaID(items.get(5).trim());
					lwp.setTestIsaQualifier(items.get(6).trim());
					//scl.addLightWellPartner(lwp);
					logger.debug("LightWellPartner object------- : " +lwp.getId());
					service.setLightWellPartner(lwp);
					//scl = serviceSubscriptionService.saveServiceCategory(scl);
					//logger.debug("saveServiceCategory object-------== : " +scl);

				}else{
					//scl.addLightWellPartner(lightWellPartner);
					logger.debug("ELSE LightWellPartner object------- : " +lightWellPartner.getId());
					service.setLightWellPartner(lightWellPartner);

				}

				//service.setLightWellPartner(scl.getLightWellPartners().iterator().next());
				BusinessService businessService = new BusinessService();
				ServiceType servType = masterDataService.findServiceTypeByName(items.get(26).trim());
				if(servType != null) {
					businessService.setServiceType(servType);
				}else {
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode41);
					logger.error(AppConstants.errorCode41);
					return ssReturnMsgBean;
				}

				if(items.get(15).trim().equalsIgnoreCase("Yes")){
					businessService.setAck(true);
					if(businessService.getServiceType().getDirection().getDirectionCode().equalsIgnoreCase("OUTBOUND")) {
						if(items.get(16) != null && items.get(16).trim().length() != 0) {
							businessService.setAckPeriod(Integer.parseInt((items.get(16).trim())));
						} else {
							businessService.setAckPeriod(36);
						}
					}
				}
				else{
					businessService.setAck(false);
				}

				Protocol p = masterDataService.findProtocolByProtocolType(items.get(14).trim());
				if(p != null) {
					if(service.getTpp() != null) {
						Set<Protocol> protocols = service.getTpp().getProtocols();
						boolean isProtocolPresent = false;
						for(Protocol protocol : protocols) {
							if(protocol.getId().compareTo(p.getId()) == 0) {
								businessService.setProtocol(p);
								isProtocolPresent = true;
								logger.debug("Protocol: "+p.getProtocolType());	
								break;

							}
							else{	 //else part added by pappu for testcase27
								//logger.debug("Protocol: is not found in DB for that 3PP Name "+protocol.getProtocolType());
								businessService.setProtocol(protocol);

								logger.debug("excel sheet Protocol: "+p.getProtocolType());	
								//break;
							}	
						}
						/*	if(!isProtocolPresent) {	// commented for testcase 27
								if(protocols.size() == 1) {
									for(Protocol protocol : protocols) {
										businessService.setProtocol(protocol);
										isProtocolPresent = true;
										logger.debug("Overwriting the protocol in template. Protocol: "+protocol.getProtocolType());	
										break;
									} 
								} else {

									ssReturnMsgBean.setReturnMsg(AppConstants.errorCode59);
									logger.error(AppConstants.errorCode59);
									return ssReturnMsgBean;	
								}
							}	*/
					} else {
						businessService.setProtocol(p);
						logger.debug("Protocol.....:"+p.getProtocolType());	
					}


				} else {
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode20);
					logger.error(AppConstants.errorCode20);
					return ssReturnMsgBean;
				}

				if(items.get(20).trim().equalsIgnoreCase("Yes")){
					businessService.setComplianceCheck(true);
					TpiMap tpiMap = masterDataService.findMapByName(items.get(21).trim());
					if(tpiMap != null) {
						businessService.setMap(tpiMap);
					} /*else {				//commenting it to allow blank Map in case of Compliance is set as True
						ssReturnMsgBean.setReturnMsg(AppConstants.errorCode56);
						logger.error(AppConstants.errorCode56);
						return ssReturnMsgBean;						
					}*/
				}else{
					businessService.setComplianceCheck(false);
				}


				businessService.setSrId(items.get(24).trim()); //should be same as Service Category Request Id
				businessService.setNotes(items.get(28).trim());		
				Version version = masterDataService.findVersionByVersionNumber(Integer.parseInt(items.get(12).trim()));
				if(version != null) {
					if(service.getTpp() != null) {
						Set<Transaction> transactions = service.getTpp().getTransactions();
						boolean isSameVersion = false;
						for(Transaction transact : transactions) {
							if(transact.getDirection().getId().compareTo(businessService.getServiceType().getDirection().getId()) == 0 && transact.getDocument().getId().compareTo(businessService.getServiceType().getDocument().getId()) == 0) {
								businessService.setVersion(transact.getVersion());
								isSameVersion = true;
								break;
							}else {		// elase part Added by pappu
								businessService.setVersion(transact.getVersion());
								break;
							}

						}
						//if(!isSameVersion) {
						//	businessService.setVersion(version);
						//}


					} else {
						businessService.setVersion(version);
					}

				} else {
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode57);
					logger.error(AppConstants.errorCode57);
					return ssReturnMsgBean;	
				}

				LightWellPartner lwpForBS = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,items.get(11).trim(),null);
				if(lwpForBS != null) {
					Set<LightWellPartner> lwpSet = scl.getLightWellPartners();
					boolean isServCatLWP = false;
					for(LightWellPartner lwpTemp : lwpSet) {
						if(lwpTemp.getId().compareTo(lwpForBS.getId()) == 0) {
							businessService.setLightWellPartner(lwpForBS);
							isServCatLWP = true;
							break;
						}
					}
					if(!isServCatLWP) {
						ssReturnMsgBean.setReturnMsg(AppConstants.errorCode62);
						logger.error(AppConstants.errorCode62);
						return ssReturnMsgBean;								
					}

				} else {
					ssReturnMsgBean.setReturnMsg(AppConstants.errorCode61);
					logger.error(AppConstants.errorCode61);
					return ssReturnMsgBean;	
				}

				if(businessService.getServiceType().getDirection().getDirectionCode().equalsIgnoreCase("OUTBOUND"))
				{
					businessService.setGsIdVersion(items.get(13).trim());
					businessService.setIsaControlNum(items.get(18).trim());
					businessService.setStControlNum(items.get(17).trim());
					businessService.setStAcceptorLookUpAlias(items.get(19).trim());
				}
				service.addBusinessService(businessService);
				
				// Start of Intercompany flag
				logger.debug("INTERCOMPANY FLAG :"+items.get(30).trim());
				if("Y".equalsIgnoreCase(items.get(30).trim())){
					serviceSubscription.setIntercompanyFlag("Y");
					if(items.get(29) == null || items.get(29).trim().length() == 0) {
						ssReturnMsgBean.setReturnMsg(AppConstants.errorCode66);
						logger.error(AppConstants.errorCode66);
						return ssReturnMsgBean;
					}
					if(items.get(29).trim().equalsIgnoreCase("All")) {
						ssReturnMsgBean.setReturnMsg(AppConstants.errorCode70);
						logger.error(AppConstants.errorCode70);
						return ssReturnMsgBean;
					}
					
					//logger.debug("INTERCOMPANY FLAG Y");
					List<SdBusinessUnit> buList = sdMasterDataService.getAllSdBysinessUnitsSortedByName();
					for(SdBusinessUnit bu : buList) {
						Set<SdBusinessSubUnit> sbuList = bu.getSubUnits();
						//logger.debug("insert sub bu unit :"+sbuList);
						for(SdBusinessSubUnit sbu : sbuList) {
							if(items.get(29).trim().equalsIgnoreCase(sbu.getSubUnitName())){
								//logger.debug("insert sub unit :"+sbu.getSubUnitName());
								service.setSdBusinessSubUnitId(sbu);
							}
						}
					}
					if(service.getSdBusinessSubUnitId() == null){
						ssReturnMsgBean.setReturnMsg(AppConstants.errorCode70);
						logger.error(AppConstants.errorCode70);
						return ssReturnMsgBean;
					}
				}else if("N".equalsIgnoreCase(items.get(30).trim())){
					serviceSubscription.setIntercompanyFlag("N");
					//logger.debug("INTERCOMPANY FLAG N");
				}
				else{
					serviceSubscription.setIntercompanyFlag("N");
					//logger.debug("INTERCOMPANY FLAG :"+serviceSubscription.getIntercompanyFlag());

				}
				
				// End of Intercompany flag
				
				serviceSubscription.addService(service);
				serviceSubscription.setServiceCategory(scl);
				try {
					serviceSubscriptionService.saveServiceSubscription(serviceSubscription);
				} catch (TpiRepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ssReturnMsgBean.setServiceSubscription(serviceSubscription);
				ssReturnMsgBean.setReturnFlag(0);
				//} create SS



			} else {
				ssReturnMsgBean.setReturnMsg(AppConstants.errorCode58);
				logger.error(AppConstants.errorCode58);
				return ssReturnMsgBean;	
			}


		}else {
			ssReturnMsgBean.setReturnMsg(AppConstants.errorCode03);
			logger.error(AppConstants.errorCode03);
		}

		if(ssReturnMsgBean.getReturnFlag() == 2 && ssReturnMsgBean.getReturnMsg().length() == 0) {
			ssReturnMsgBean.setReturnMsg("Nothing to update.");
		}

		return ssReturnMsgBean;
	}

	private SeedDataRespMsg processSeedDataMultiTabXlsxFile(MultipartFile file, String entityName) throws IOException {
		logger.debug("Invoked SeedDataLoadServiceImpl.processSeedDataMultiTabXlsxFile().");
		int counterCreated = 0;
		int counterUpdated = 0;

		//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
		int counterSkipped = 0;
		SeedDataRespMsg seedDataRespMsg = new SeedDataRespMsg();
		ArrayList<SeedDataInsertStatMsg> seedDataInsertStatMsgList = new ArrayList<SeedDataInsertStatMsg>();


		String message= "Processing " + entityName;
		logger.debug("Prcessing Seed Data");


		seedDataRespMsg.setMessage(message); 

		BufferedReader reader = null;

		if (file != null && entityName!=null && !file.isEmpty()) 
		{
			//Added by Arindam Sikdar for Dynamic Data Load process - Partner Load
			logger.debug("File Name: " + file.getOriginalFilename());
			if(file.getOriginalFilename().contains(".xlsx")) {
				Workbook workbook = new XSSFWorkbook(file.getInputStream());
				SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();

				String entityNameUpper = entityName.toUpperCase();
				logger.debug("Processing data for : " + entityNameUpper); 

				switch (entityNameUpper) 
				{
				case "PROMOTION":  
					//Partner tab processing
					Sheet datatypeSheetPartner = workbook.getSheet("Partner");
					List<String> partnerList = new ArrayList();
					Iterator<Row> iterator = datatypeSheetPartner.iterator();
					String rowLine = null;
					StringBuffer sb = null;
					int lastColNum = 0;
					//int firstColNum = 0;
					boolean isHeaderRow = true;
					while (iterator.hasNext()) {
						rowLine = "";
						sb = new StringBuffer();
						Row currentRow = iterator.next();
						if(isHeaderRow) {
							lastColNum = currentRow.getLastCellNum();
							isHeaderRow = false;
							logger.debug("Skipping Hearder Row Number: " + currentRow.getRowNum());
							continue;
						}
						for(int i=0; i < lastColNum; i++) {
							Cell currentCell = currentRow.getCell(i, Row.RETURN_NULL_AND_BLANK); //cellIterator.next();
							if(currentCell != null) {
								switch(currentCell.getCellType()) 
								{
								case XSSFCell.CELL_TYPE_STRING:
									sb.append(currentCell.getStringCellValue()+",");
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									sb.append(new Double(currentCell.getNumericCellValue()).longValue()+",");
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									sb.append(",");
									break;	
								case XSSFCell.CELL_TYPE_BOOLEAN:
									sb.append(Boolean.toString(currentCell.getBooleanCellValue())+",");
									break;								
								default:
									sb.append(",");
									break;

								}
							} else {
								sb.append(",");
							}
						}
						rowLine = sb.toString();
						logger.debug("RowLine: " + rowLine);

						seedDataInsertStatMsg = new SeedDataInsertStatMsg();
						PartnerUtilBean partnerUtilBean = loadPartnerForPromotion(rowLine, partnerList);
						seedDataInsertStatMsg = partnerUtilBean.getSeedDataInsertStatMsg();
						if(partnerUtilBean.getPartnerName().length() > 0) {
							partnerList.add(partnerUtilBean.getPartnerName());
						}
						int rowCount = currentRow.getRowNum()+1;
						seedDataInsertStatMsg.setRowNum(rowCount);
						if (seedDataInsertStatMsg.getReturnFlag() == 0)
						{
							counterCreated++;
						}
						else if(seedDataInsertStatMsg.getReturnFlag() == 1)
						{
							counterUpdated++;
						}
						else 
						{
							counterSkipped++;
							seedDataInsertStatMsgList.add(seedDataInsertStatMsg);
						}
					}	

					//3PP tab processing 
					Sheet datatypeSheetTpp = workbook.getSheet("3PP");
					List<String> tppList = new ArrayList();
					iterator= datatypeSheetTpp.iterator();
					rowLine = null;
					sb = null;
					lastColNum = 0;
					//int firstColNum = 0;
					isHeaderRow = true;
					while (iterator.hasNext()) {
						rowLine = "";
						sb = new StringBuffer();
						Row currentRow = iterator.next();
						if(isHeaderRow) {
							lastColNum = currentRow.getLastCellNum();
							isHeaderRow = false;
							logger.debug("Skipping Hearder Row Number: " + currentRow.getRowNum());
							continue;
						}
						for(int i=0; i < lastColNum; i++) {
							Cell currentCell = currentRow.getCell(i, Row.RETURN_NULL_AND_BLANK); //cellIterator.next();
							if(currentCell != null) {
								switch(currentCell.getCellType()) 
								{
								case XSSFCell.CELL_TYPE_STRING:
									sb.append(currentCell.getStringCellValue()+",");
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									sb.append(new Double(currentCell.getNumericCellValue()).longValue()+",");
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									sb.append(",");
									break;	
								case XSSFCell.CELL_TYPE_BOOLEAN:
									sb.append(Boolean.toString(currentCell.getBooleanCellValue())+",");
									break;								
								default:
									sb.append(",");
									break;

								}
							} else {
								sb.append(",");
							}
						}
						rowLine = sb.toString();
						logger.debug("RowLine: " + rowLine);

						seedDataInsertStatMsg = new SeedDataInsertStatMsg();
						TPPUtilBean tppUtilBean = loadTPPForPromotion(rowLine, tppList);						
						seedDataInsertStatMsg = tppUtilBean.getSeedDataInsertStatMsg();
						if(tppUtilBean.getTppName().length() > 0) {
							tppList.add(tppUtilBean.getTppName());
						}
						int rowCount = currentRow.getRowNum()+1;
						seedDataInsertStatMsg.setRowNum(rowCount);
						if (seedDataInsertStatMsg.getReturnFlag() == 0)
						{
							counterCreated++;
						}
						else if(seedDataInsertStatMsg.getReturnFlag() == 1)
						{
							counterUpdated++;
						}
						else 
						{
							counterSkipped++;
							seedDataInsertStatMsgList.add(seedDataInsertStatMsg);
						}
					}

					//Service Subscription tab
					Sheet datatypeSheetSrvcSubs = workbook.getSheet("ServiceSubscription");
					iterator= datatypeSheetSrvcSubs.iterator();
					rowLine = null;
					sb = null;
					lastColNum = 0;
					//int firstColNum = 0;
					isHeaderRow = true;
					while (iterator.hasNext()) {
						rowLine = "";
						sb = new StringBuffer();
						Row currentRow = iterator.next();
						if(isHeaderRow) {
							lastColNum = currentRow.getLastCellNum();
							isHeaderRow = false;
							logger.debug("Skipping Hearder Row Number: " + currentRow.getRowNum());
							continue;
						}
						for(int i=0; i < lastColNum; i++) {
							Cell currentCell = currentRow.getCell(i, Row.RETURN_NULL_AND_BLANK); //cellIterator.next();
							if(currentCell != null) {
								switch(currentCell.getCellType()) 
								{
								case XSSFCell.CELL_TYPE_STRING:
									sb.append(currentCell.getStringCellValue()+",");
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									sb.append(new Double(currentCell.getNumericCellValue()).longValue()+",");
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									sb.append(",");
									break;	
								case XSSFCell.CELL_TYPE_BOOLEAN:
									sb.append(Boolean.toString(currentCell.getBooleanCellValue())+",");
									break;								
								default:
									sb.append(",");
									break;

								}
							} else {
								sb.append(",");
							}
						}
						rowLine = sb.toString();
						logger.debug("RowLine: " + rowLine);

						seedDataInsertStatMsg = new SeedDataInsertStatMsg();
						seedDataInsertStatMsg = loadServiceSubscription(rowLine);
						int rowCount = currentRow.getRowNum()+1;
						seedDataInsertStatMsg.setRowNum(rowCount);
						if (seedDataInsertStatMsg.getReturnFlag() == 0)
						{
							counterCreated++;
						}
						else if(seedDataInsertStatMsg.getReturnFlag() == 1)
						{
							counterUpdated++;
						}
						else 
						{
							counterSkipped++;
							seedDataInsertStatMsgList.add(seedDataInsertStatMsg);
						}
					}				

					break;
				default:
					break;
				}

			}
		}
		message = "Total created: " + counterCreated + " Total updated: " + counterUpdated + " Total skipped: " + counterSkipped;
		seedDataRespMsg.setMessage(message);
		seedDataRespMsg.setSeedDataInsertStatMsgList(seedDataInsertStatMsgList);
		return seedDataRespMsg;
	}

	private PartnerUtilBean loadPartnerForPromotion(String line, List<String> partnerList){
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		PartnerUtilBean partnerUtilBean = new PartnerUtilBean();
		String validationStatus = validatePartnerString(line);
		if(validationStatus.length() == 0) {
			Partner partner = seedDataService.getPartnerFromXlsxString(line);
			Partner result = null;		
			if(partner != null && partner.getPartnerName() != AppConstants.errorCode01 && partner.getPartnerName() != AppConstants.errorCode10 ) {
				boolean isDuplicatePartner = false;
				for(String partnerName : partnerList) {
					if(partnerName.equalsIgnoreCase(partner.getPartnerName())) {
						isDuplicatePartner = true;
					}
				}
				if(!isDuplicatePartner) {
					partnerUtilBean.setPartnerName(partner.getPartnerName().trim());
				} else {
					partnerUtilBean.setPartnerName("");
				}

				if(!partnerService.isPartnerExist(partner.getPartnerName())) {
					try {

						result = partnerService.createPartner(partner);
					} catch (TpiRepositoryException e) {
						// TODO Auto-generated catch block
						logger.error(AppConstants.errorCode02, e);
						returnFlag = 2;
						seedDataInsertStatMsg.setStatusMsg(AppConstants.errorCode02);
						e.printStackTrace();
					}
				}else {
					Partner existingPartner = partnerService.findPartnerByUniqueName(partner.getPartnerName());
					Set<ContactDetail> set = partner.getContactDetails();
					Set<ContactDetail> existingSet = existingPartner.getContactDetails();
					if(!isDuplicatePartner) {
						existingPartner.removeContactDetails(existingSet);
					} 
					for (ContactDetail s : set) {						
						existingPartner.addContact(s);
					}
					Set<ContactDetail> newSet = existingPartner.getContactDetails();
					for(ContactDetail s : newSet) {
						logger.debug("Contact Name: " + s.getContactName());
					}

					//}


					existingPartner.setPartnerGroup(partner.getPartnerGroup());
					result = partnerService.updatePartner(existingPartner);
					returnFlag = 1;
				}
			} else {
				returnFlag = 2;
				if(partner != null && partner.getPartnerName() == AppConstants.errorCode01) {
					seedDataInsertStatMsg.setStatusMsg(AppConstants.errorCode01);
				}
				if(partner != null && partner.getPartnerName() == AppConstants.errorCode10) {
					seedDataInsertStatMsg.setStatusMsg(AppConstants.errorCode10);
				}
			}
		} else {
			returnFlag = 2;
			seedDataInsertStatMsg.setStatusMsg(validationStatus);
		}

		seedDataInsertStatMsg.setReturnFlag(returnFlag);
		partnerUtilBean.setSeedDataInsertStatMsg(seedDataInsertStatMsg);
		return partnerUtilBean;
	}

	private TPPUtilBean loadTPPForPromotion(String line, List<String> tppList) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		TPPUtilBean tppUtilBean = new TPPUtilBean();
		TPPReturnMsgBean tppReturnMsgBean = seedDataService.getTPPFromXlsxStringForPromotion(line, tppList);
		if(tppReturnMsgBean.getTpp() == null) {
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg(tppReturnMsgBean.getReturnMsg());
		} else {
			seedDataInsertStatMsg.setReturnFlag(tppReturnMsgBean.getReturnFlag());
			boolean isDuplicateTpp = false;
			for(String tppName : tppList) {
				if(tppName.equalsIgnoreCase(tppReturnMsgBean.getTpp().getName())) {
					isDuplicateTpp = true;
				}
			}
			if(!isDuplicateTpp) {
				tppUtilBean.setTppName(tppReturnMsgBean.getTpp().getName());
			} else {
				tppUtilBean.setTppName("");
			}
		}

		tppUtilBean.setSeedDataInsertStatMsg(seedDataInsertStatMsg);
		return tppUtilBean;
	}

	@Override	
	public TPPReturnMsgBean getTPPFromXlsxStringForPromotion(String tppString, List<String> tppList) {
		TPPReturnMsgBean tppReturnMsgBean1 = new TPPReturnMsgBean();
		tppReturnMsgBean1.setReturnMsg("");
		tppReturnMsgBean1.setReturnFlag(2);
		Tpp tpp = null;

		List<String> items = Arrays.asList(tppString.split("\\s*,\\s*",-1));
		//List<String> items = Arrays.asList(tppString.split(","));	
		logger.debug("Inside getTPPFromXlsxString() method.");
		logger.debug("Column Size: " + items.size());
		if(items.size() == 35) {
			if(items.get(0) == null || items.get(0).trim().length() == 0) {
				tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode11);
				logger.error(AppConstants.errorCode11);
				return tppReturnMsgBean1;
			} 
			if(items.get(1) == null || items.get(1).trim().length() == 0) {
				tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode12);
				logger.error(AppConstants.errorCode12);
				return tppReturnMsgBean1;
			} 
			if(items.get(19) != null && items.get(19).trim().length() != 0) {
				if(items.get(16) != null&& items.get(16).trim().length() != 0 && items.get(17) != null&& items.get(17).trim().length() != 0 && items.get(18) != null&& items.get(18).trim().length() != 0) {
					if((items.get(16).trim().equals(items.get(17).trim())) || (items.get(16).trim().equals(items.get(18).trim())) || (items.get(16).trim().equals(items.get(19).trim())) ) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
						return tppReturnMsgBean1;
					} else if((items.get(17).trim().equals(items.get(18).trim())) || (items.get(17).trim().equals(items.get(19).trim()))) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
						return tppReturnMsgBean1;
					} else if((items.get(18).trim().equals(items.get(19).trim()))) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
						return tppReturnMsgBean1;
					}

				} 
			} else {
				if(items.get(16) != null && items.get(16).trim().length() != 0 && items.get(17) != null && items.get(17).trim().length() != 0 && items.get(18) != null && items.get(18).trim().length() != 0) {
					if((items.get(16).trim().equals(items.get(17).trim())) || (items.get(16).trim().equals(items.get(18).trim())) || (items.get(17).trim().equals(items.get(18).trim())) ) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
						return tppReturnMsgBean1;
					}

				} 
			}
			if(Integer.parseInt(items.get(1)) != 3) { //TPPType vs Protocols validation
				for(int k=9; k<=12 ; k++) {
					if(items.get(k) != null && items.get(k).trim().length() != 0) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode30);
						return tppReturnMsgBean1;
					}
				}
			} 
			if(Integer.parseInt(items.get(1)) != 1) { //TPPType vs ISA, GS Ids
				for(int k=2; k<=7 ; k++) {
					if(items.get(k) != null && items.get(k).trim().length() != 0) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode37);
						return tppReturnMsgBean1;
					}
				}
			} 
			//else {
			boolean isDuplicateTpp = false;
			for(String tppName : tppList) {
				if(tppName.equalsIgnoreCase(items.get(0).trim())) {
					isDuplicateTpp = true;
				}
			}
			tpp = tppService.findByNameFullStringMatchIgnoreCase(items.get(0));
			if(tpp == null) {
				//new insert
				String validationStatus = validateTPPString(tppString);
				if(validationStatus != null && validationStatus.length() != 0) {
					tppReturnMsgBean1.setReturnMsg(validationStatus);
					return tppReturnMsgBean1;
				}

				//Type Insert
				//========================================================================================
				logger.debug("Inside TPP Insert Process");
				tpp = new Tpp();
				tpp.setName(items.get(0).trim());
				List<TppType> tppTypes = masterDataService.findAllTppTypes();
				Iterator<TppType> itr = tppTypes.iterator();
				TppType tppType = null;
				while(itr.hasNext()) {
					tppType = itr.next();

					if(tppType.getTypeCode() == Short.valueOf(items.get(1).trim())) {
						tpp.setType(tppType);
					}
				}
				if(tpp.getType().getTypeCode() == 2 || tpp.getType().getTypeCode() == 3) {
					tpp.setLightWellPartner(null);
				} else {
					if(items.get(2) == null || items.get(2).trim().length() == 0) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode14);
						logger.error(AppConstants.errorCode14);
						return tppReturnMsgBean1;
					} else if(items.get(3) == null || items.get(3).trim().length() == 0)  {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode15);
						logger.error(AppConstants.errorCode15);
						return tppReturnMsgBean1;
					} else if(items.get(4) == null || items.get(4).trim().length() == 0)  {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode16);
						logger.error(AppConstants.errorCode16);
						return tppReturnMsgBean1;
					} else if(items.get(5) == null || items.get(5).trim().length() == 0)  {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode17);
						logger.error(AppConstants.errorCode17);
						return tppReturnMsgBean1;
					} else if(items.get(6) == null || items.get(6).trim().length() == 0)  {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode18);
						logger.error(AppConstants.errorCode18);
						return tppReturnMsgBean1;
					} else if(items.get(7) == null || items.get(7).trim().length() == 0)  {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode19);
						logger.error(AppConstants.errorCode19);
						return tppReturnMsgBean1;
					} else {
						List<Long> tppsForLWInsert = tppService.getTppsForAbcId(items.get(2), items.get(4), items.get(5), items.get(7));
						if (tppsForLWInsert ==null || tppsForLWInsert.isEmpty())
						{							
							LightWellPartner lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(items.get(2).trim(),null,null,null,null);
							if(lightWellPartner == null) {
								lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,items.get(4).trim(),null,null,null);
								if(lightWellPartner == null) {
									lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,items.get(5).trim(),null,null);
									if(lightWellPartner == null) {
										lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,items.get(7).trim(),null);
										if(lightWellPartner != null) {
											tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
											logger.error(AppConstants.errorCode35);
											return tppReturnMsgBean1;	
										} 
									} else {
										tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
										logger.error(AppConstants.errorCode35);
										return tppReturnMsgBean1;								
									}
								} else {
									tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
									logger.error(AppConstants.errorCode35);
									return tppReturnMsgBean1;								
								}
							} else {
								tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
								logger.error(AppConstants.errorCode35);
								return tppReturnMsgBean1;								
							}

						}
						else
						{
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
							logger.error(AppConstants.errorCode35);
							return tppReturnMsgBean1;
						}
						LightWellPartner lwPartner = seedDataService.getLightWellPartnerFromCsvString("0,"+items.get(2)+","+items.get(3)+","+items.get(4)+","+items.get(5)+","+items.get(6)+","+items.get(7)+","+"ABC_S_ABDC,1,Dataload,");
						if(lwPartner != null) {									
							tpp.setLightWellPartner(lwPartner);
						} else {
							tppReturnMsgBean1.setReturnMsg("Error during LW Partner Object creation.");
							logger.error("Error during LW Partner Object creation.");
							return tppReturnMsgBean1;
						}						


					}
				}

				//========================================================================================
				//Protocol Insert
				//========================================================================================
				if(Integer.parseInt(items.get(1)) == 3) {
					for(int i=8; i<=12; i++) {
						if(items.get(i) != null && items.get(i).trim().length() != 0){
							Protocol pNew = masterDataService.findProtocolByProtocolType(items.get(i));
							if(pNew == null) {
								tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode20);
								logger.error(AppConstants.errorCode20);
								return tppReturnMsgBean1;
							}
							tpp.addProtocol(pNew);						
						}
					}
				} else {
					for(int n=9;n<=12;n++) {
						if(items.get(n) != null && items.get(n).trim().length() != 0) {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode30);
							logger.error(AppConstants.errorCode30);
							return tppReturnMsgBean1;
						}
					}
					Protocol pNew = masterDataService.findProtocolByProtocolType(items.get(8));
					if(pNew == null) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode20);
						logger.error(AppConstants.errorCode20);
						return tppReturnMsgBean1;
					}
					tpp.addProtocol(pNew);	
				}


				//========================================================================================
				//Transaction Insert
				//========================================================================================
				Direction direction = masterDataService.findDirectionByName(items.get(13).trim());
				if(direction == null) {
					tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode24);
					logger.error(AppConstants.errorCode24);
					return tppReturnMsgBean1;
				}
				Document document = masterDataService.findDocumentTypeByDocumentType(items.get(14).trim());
				if(document == null) {
					tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode25);
					logger.error(AppConstants.errorCode25);
					return tppReturnMsgBean1;
				}
				Version version = masterDataService.findVersionByVersionNumber(Integer.parseInt(items.get(15).trim()));
				if(version == null) {
					tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode26);
					logger.error(AppConstants.errorCode26);
					return tppReturnMsgBean1;
				}
				Transaction transaction = new Transaction();
				transaction.setDirection(direction);
				transaction.setDocument(document);
				transaction.setVersion(version);
				tpp.addTransaction(transaction);
				//========================================================================================
				//Delimiter values insert
				//========================================================================================												

				List<Delimiter> segmentDelimiters = masterDataService.getSegmentDelimiters();
				Iterator<Delimiter> itr1 = segmentDelimiters.iterator();
				while(itr1.hasNext()) {
					Delimiter delim = itr1.next();
					if(delim.getDelimiter().equals(items.get(16).trim())) {
						tpp.setSegmentDelimiter(delim);
					}
				}							

				List<Delimiter> elementDelimiters = masterDataService.getElementDelimiters();
				Iterator<Delimiter> itr2 = elementDelimiters.iterator();
				while(itr2.hasNext()) {
					Delimiter delim = itr2.next();
					if(delim.getDelimiter().equals(items.get(17).trim())) {
						tpp.setElementDelimiter(delim);
					}
				}						

				List<Delimiter> compositeElementDelimiters = masterDataService.getCompositeDelimiters();
				Iterator<Delimiter> itr3 = compositeElementDelimiters.iterator();
				while(itr3.hasNext()) {
					Delimiter delim = itr3.next();
					if(delim.getDelimiter().equals(items.get(18).trim())) {
						tpp.setCompositeElementDelimiter(delim);
					}
				}						

				List<Delimiter> repeatDelimiters = masterDataService.getRepeatDeleimiters();
				Iterator<Delimiter> itr4 = repeatDelimiters.iterator();
				while(itr4.hasNext()) {
					Delimiter delim = itr4.next();
					if(delim.getDelimiter().equals(items.get(19).trim())) {
						tpp.setRepeatDelimiter(delim);
					}
				}						

				//========================================================================================
				//ContactDetails Insert
				//========================================================================================
				ContactDetail contactDetail = seedDataService.getContactDetailFromCsvString(items.get(20)+","+items.get(21)+","+items.get(22)+","+items.get(23)+","+items.get(24)+","+items.get(25)+","+items.get(26)+","+items.get(27)+","+items.get(28)+","+items.get(29)+","+items.get(30)+","+items.get(31)+","+items.get(32)+","+items.get(33)+",");
				tpp.addContact(contactDetail);					
				//========================================================================================
				tppReturnMsgBean1.setTpp(tppService.saveTpp(tpp));				
				tppReturnMsgBean1.setReturnFlag(0);

			} else {
				//update
				logger.debug("Inside TPP Update Process");
				//check if any change in type
				//========================================================================================
				if(tpp.getType().getTypeCode() != Integer.parseInt(items.get(1).trim())) {
					List<TppType> tppTypes = masterDataService.findAllTppTypes();
					Iterator<TppType> itr = tppTypes.iterator();
					TppType tppType = null;
					while(itr.hasNext()) {
						tppType = itr.next();
						if(tppType.getTypeCode() == Short.valueOf(items.get(1).trim())) {
							tpp.setType(tppType);
						}
					}
					if(tpp.getType().getTypeCode() == Short.valueOf("2") || tpp.getType().getTypeCode() == Short.valueOf("3")) {
						tpp.setLightWellPartner(null);
					} else {
						if(items.get(2) == null || items.get(2).trim().length() == 0) {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode14);
							logger.error(AppConstants.errorCode14);
							return tppReturnMsgBean1;
						} else if(items.get(3) == null || items.get(3).trim().length() == 0)  {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode15);
							logger.error(AppConstants.errorCode15);
							return tppReturnMsgBean1;
						} else if(items.get(4) == null || items.get(4).trim().length() == 0)  {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode16);
							logger.error(AppConstants.errorCode16);
							return tppReturnMsgBean1;
						} else if(items.get(5) == null || items.get(5).trim().length() == 0)  {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode17);
							logger.error(AppConstants.errorCode17);
							return tppReturnMsgBean1;
						} else if(items.get(6) == null || items.get(6).trim().length() == 0)  {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode18);
							logger.error(AppConstants.errorCode18);
							return tppReturnMsgBean1;
						} else if(items.get(7) == null || items.get(7).trim().length() == 0)  {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode19);
							logger.error(AppConstants.errorCode19);
							return tppReturnMsgBean1;
						} else {
							List<Long> tppsForLW = tppService.getTppsForAbcId(items.get(2), items.get(4), items.get(5), items.get(7));
							if (tppsForLW ==null || tppsForLW.isEmpty())
							{
								LightWellPartner lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(items.get(2).trim(),null,null,null,null);
								if(lightWellPartner == null) {
									lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,items.get(4).trim(),null,null,null);
									if(lightWellPartner == null) {
										lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,items.get(5).trim(),null,null);
										if(lightWellPartner == null) {
											lightWellPartner = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,items.get(7).trim(),null);
											if(lightWellPartner != null) {
												tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
												logger.error(AppConstants.errorCode35);
												return tppReturnMsgBean1;	
											} 
										} else {
											tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
											logger.error(AppConstants.errorCode35);
											return tppReturnMsgBean1;								
										}
									} else {
										tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
										logger.error(AppConstants.errorCode35);
										return tppReturnMsgBean1;								
									}
								} else {
									tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
									logger.error(AppConstants.errorCode35);
									return tppReturnMsgBean1;								
								}

							}
							else
							{
								tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode35);
								logger.error(AppConstants.errorCode35);
								return tppReturnMsgBean1;
							}	

							LightWellPartner lwPartner = seedDataService.getLightWellPartnerFromCsvString("0,"+items.get(2)+","+items.get(3)+","+items.get(4)+","+items.get(5)+","+items.get(6)+","+items.get(7)+","+"ABC_S_ABDC,1,Dataload,");
							if(lwPartner != null) {									
								tpp.setLightWellPartner(lwPartner);
							} else {
								tppReturnMsgBean1.setReturnMsg("Error during LW Partner Object creation.");
								logger.error("Error during LW Partner Object creation.");
								return tppReturnMsgBean1;
							}						


						}
					}
				} else { //check if ISA , GS IDs are same or not
					if(tpp.getType().getTypeCode() == 1 ) {
						int emptyColCount = 0;
						for(int p = 2; p<=7 ; p++) {
							if(items.get(p) == null || items.get(p).trim().length() == 0) {
								emptyColCount++;
							} 
						}
						if(emptyColCount != 0 && emptyColCount != 6) {
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode36);
							logger.error(AppConstants.errorCode36);
							return tppReturnMsgBean1;
						}

						if(emptyColCount == 0) {
							//anything

							LightWellPartner tppLWP = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(items.get(2).trim(),null,null,null,null);
							if(tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) == 0) {	
								logger.debug("Updating existing LW Partner Object based on TESTIsaID.");
								tpp.getLightWellPartner().setTestIsaID(items.get(2).trim());
								tpp.getLightWellPartner().setTestIsaQualifier(items.get(3).trim());
								tpp.getLightWellPartner().setTestGsId(items.get(4).trim());
								tpp.getLightWellPartner().setProductionIsaID(items.get(5).trim());
								tpp.getLightWellPartner().setProductionIsaQualifier(items.get(6).trim());
								tpp.getLightWellPartner().setProductionGsId(items.get(7).trim());
							} else if (tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) != 0) {
								tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode63);
								logger.error(AppConstants.errorCode63);
								return tppReturnMsgBean1;
							} else {
								tppLWP = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,items.get(4).trim(),null,null,null);
								if(tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) == 0) {	
									logger.debug("Updating existing LW Partner Object based on TESTGSID.");
									tpp.getLightWellPartner().setTestIsaID(items.get(2).trim());
									tpp.getLightWellPartner().setTestIsaQualifier(items.get(3).trim());
									tpp.getLightWellPartner().setTestGsId(items.get(4).trim());
									tpp.getLightWellPartner().setProductionIsaID(items.get(5).trim());
									tpp.getLightWellPartner().setProductionIsaQualifier(items.get(6).trim());
									tpp.getLightWellPartner().setProductionGsId(items.get(7).trim());
								} else if (tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) != 0) {
									tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode63);
									logger.error(AppConstants.errorCode63);
									return tppReturnMsgBean1;
								} else {
									tppLWP = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,items.get(5).trim(),null,null);
									if(tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) == 0) {	
										logger.debug("Updating existing LW Partner Object based on PRODIsaID.");
										tpp.getLightWellPartner().setTestIsaID(items.get(2).trim());
										tpp.getLightWellPartner().setTestIsaQualifier(items.get(3).trim());
										tpp.getLightWellPartner().setTestGsId(items.get(4).trim());
										tpp.getLightWellPartner().setProductionIsaID(items.get(5).trim());
										tpp.getLightWellPartner().setProductionIsaQualifier(items.get(6).trim());
										tpp.getLightWellPartner().setProductionGsId(items.get(7).trim());
									} else if (tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) != 0) {
										tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode63);
										logger.error(AppConstants.errorCode63);
										return tppReturnMsgBean1;
									} else {
										tppLWP = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,items.get(7).trim(),null);
										if(tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) == 0) {	
											logger.debug("Updating existing LW Partner Object based on PRODGSID.");
											tpp.getLightWellPartner().setTestIsaID(items.get(2).trim());
											tpp.getLightWellPartner().setTestIsaQualifier(items.get(3).trim());
											tpp.getLightWellPartner().setTestGsId(items.get(4).trim());
											tpp.getLightWellPartner().setProductionIsaID(items.get(5).trim());
											tpp.getLightWellPartner().setProductionIsaQualifier(items.get(6).trim());
											tpp.getLightWellPartner().setProductionGsId(items.get(7).trim());
										} else if (tppLWP != null && tpp.getLightWellPartner().getId().compareTo(tppLWP.getId()) != 0) {
											tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode63);
											logger.error(AppConstants.errorCode63);
											return tppReturnMsgBean1;
										} else {
											tpp.getLightWellPartner().setTestIsaID(items.get(2).trim());
											tpp.getLightWellPartner().setTestIsaQualifier(items.get(3).trim());
											tpp.getLightWellPartner().setTestGsId(items.get(4).trim());
											tpp.getLightWellPartner().setProductionIsaID(items.get(5).trim());
											tpp.getLightWellPartner().setProductionIsaQualifier(items.get(6).trim());
											tpp.getLightWellPartner().setProductionGsId(items.get(7).trim());
										}
									}
								}
							}


						}

					}

				}
				//========================================================================================

				//check if any change in protocols
				//========================================================================================
				if(items.get(8) != null && items.get(8).trim().length() != 0) {
					Set<Protocol> protocols = tpp.getProtocols();
					HashSet<Protocol> newProtocols = new HashSet<Protocol>();
					//if(tpp.getType().getTypeCode() == 3) {
					if(tpp.getType().getTypeCode().compareTo(new Short("3")) == 0) {
						for(int j=8;j<= 12;j++) {
							if(items.get(j) != null && items.get(j).trim().length() != 0) {
								Protocol pNew = masterDataService.findProtocolByProtocolType(items.get(j));
								if(pNew == null) {
									tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode20);
									logger.error(AppConstants.errorCode20);
									return tppReturnMsgBean1;
								}
								newProtocols.add(pNew);
							}
						}
						tpp.setProtocols(newProtocols);
					} else {
						for(int n=9;n<=12;n++) {
							if(items.get(n) != null && items.get(n).trim().length() != 0) {
								tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode30);
								logger.error(AppConstants.errorCode30);
								return tppReturnMsgBean1;
							}
						}
						boolean isPresent = false;
						for(Protocol p : protocols) {
							if(p.getProtocolType().equalsIgnoreCase(items.get(8))) {							
								isPresent = true;
							}
						}
						if(!isPresent) {
							Protocol pNew = masterDataService.findProtocolByProtocolType(items.get(8));
							if(pNew == null) {
								tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode20);
								logger.error(AppConstants.errorCode20);
								return tppReturnMsgBean1;
							}
							//protocols = new HashSet<Protocol>();
							newProtocols.add(pNew);
							tpp.setProtocols(newProtocols);
							//tpp.addProtocol(pNew);						
						}
					}
				}
				//========================================================================================

				//transaction
				//========================================================================================					
				StringBuffer sb = new StringBuffer("");
				int i = 0;
				if(items.get(13) == null || items.get(13).trim().length() == 0) {						
					sb.append(AppConstants.errorCode21);
					i++;
					//logger.error(AppConstants.errorCode21);
				} 
				if(items.get(14) == null || items.get(14).trim().length() == 0) {						
					sb.append(AppConstants.errorCode22);
					i++;
				} 
				if(items.get(15) == null || items.get(15).trim().length() == 0) {						
					sb.append(AppConstants.errorCode23);
					i++;
				}					
				if(i == 1 || i == 2) {
					tppReturnMsgBean1.setReturnMsg(sb.toString());
					logger.error(sb.toString());
					return tppReturnMsgBean1;
				}
				if(i == 0) {
					Direction direction = masterDataService.findDirectionByName(items.get(13).trim());
					if(direction == null) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode24);
						logger.error(AppConstants.errorCode24);
						return tppReturnMsgBean1;
					}
					Document document = masterDataService.findDocumentTypeByDocumentType(items.get(14).trim());
					if(document == null) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode25);
						logger.error(AppConstants.errorCode25);
						return tppReturnMsgBean1;
					}
					Version version = masterDataService.findVersionByVersionNumber(Integer.parseInt(items.get(15).trim()));
					if(version == null) {
						tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode26);
						logger.error(AppConstants.errorCode26);
						return tppReturnMsgBean1;
					}
					Transaction transaction = new Transaction();
					transaction.setDirection(direction);
					transaction.setDocument(document);
					transaction.setVersion(version);
					Set<Transaction> transactions = tpp.getTransactions();
					if(!isDuplicateTpp) {
						tpp.removeTransactions(transactions);
						tpp.addTransaction(transaction);
					} else {					
						if(transactions != null) {
							Iterator<Transaction> transactionItr = transactions.iterator();
							Transaction existingTransaction = null;
							while(transactionItr.hasNext()) {
								existingTransaction = transactionItr.next();
								if((existingTransaction.getDirection().getId() == direction.getId()) && (existingTransaction.getDocument().getId() == document.getId()) && (existingTransaction.getVersion().getId() == version.getId())) {
									logger.error(AppConstants.errorCode28);
									tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode28);
									return tppReturnMsgBean1;
								} /*else {
								tpp.addTransaction(transaction);
							}*/
							}
							tpp.addTransaction(transaction);
						}
					}
				}
				//========================================================================================

				//Delimiter values
				//========================================================================================
				int delimCount = 0;
				for(int o=16; o<=18; o++) {
					if(items.get(o) != null && items.get(o).trim().length() != 0) {
						delimCount++;
					}
				}
				if(delimCount == 0 && items.get(19) != null && items.get(19).trim().length() != 0) {
					logger.error(AppConstants.errorCode38);
					tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode38);
					return tppReturnMsgBean1;
				}
				if(delimCount > 0 && delimCount < 3) {
					logger.error(AppConstants.errorCode38);
					tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode38);
					return tppReturnMsgBean1;					
				}
				if(items.get(16) != null && items.get(16).trim().length() != 0) {						
					if(!(tpp.getSegmentDelimiter().getDelimiter()).equals(items.get(16).trim())) {
						/*						if(tpp.getElementDelimiter().getDelimiter().equals(items.get(16).trim()) || tpp.getCompositeElementDelimiter().getDelimiter().equals(items.get(16).trim()) || tpp.getRepeatDelimiter().getDelimiter().equals(items.get(16).trim())) {
							logger.error(AppConstants.errorCode29);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
							return tppReturnMsgBean1;
						} else {*/
						List<Delimiter> segmentDelimiters = masterDataService.getSegmentDelimiters();
						Iterator<Delimiter> itr = segmentDelimiters.iterator();
						boolean isIncorrectSegDelim = true;
						while(itr.hasNext()) {
							Delimiter delim = itr.next();
							if(delim.getDelimiter().equals(items.get(16).trim())) {
								tpp.setSegmentDelimiter(delim);
								isIncorrectSegDelim = false;
							}
						}			
						if(isIncorrectSegDelim) {
							logger.error(AppConstants.errorCode31);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode31);
							return tppReturnMsgBean1;
						}
						//}
					}
				}

				if(items.get(17) != null && items.get(17).trim().length() != 0) {						
					if(!(tpp.getElementDelimiter().getDelimiter()).equals(items.get(17).trim())) {
						/*						if(tpp.getSegmentDelimiter().getDelimiter().equals(items.get(17).trim()) || tpp.getCompositeElementDelimiter().getDelimiter().equals(items.get(17).trim()) || tpp.getRepeatDelimiter().getDelimiter().equals(items.get(17).trim())) {
							logger.error(AppConstants.errorCode29);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
							return tppReturnMsgBean1;
						} else {*/
						List<Delimiter> elementDelimiters = masterDataService.getElementDelimiters();
						Iterator<Delimiter> itr = elementDelimiters.iterator();
						boolean isIncorrectElemDelim = true;
						while(itr.hasNext()) {
							Delimiter delim = itr.next();
							if(delim.getDelimiter().equals(items.get(17).trim())) {
								tpp.setElementDelimiter(delim);
								isIncorrectElemDelim = false;
							}
						}
						if(isIncorrectElemDelim) {
							logger.error(AppConstants.errorCode32);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode32);
							return tppReturnMsgBean1;								
						}
						//}
					}
				}

				if(items.get(18) != null && items.get(18).trim().length() != 0) {						
					if(!(tpp.getCompositeElementDelimiter().getDelimiter()).equals(items.get(18).trim())) {
						/*						if(tpp.getElementDelimiter().getDelimiter().equals(items.get(18).trim()) || tpp.getRepeatDelimiter().getDelimiter().equals(items.get(18).trim()) || tpp.getSegmentDelimiter().getDelimiter().equals(items.get(18).trim())) {
							logger.error(AppConstants.errorCode29);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
							return tppReturnMsgBean1;
						} else {*/
						List<Delimiter> compositeElementDelimiters = masterDataService.getCompositeDelimiters();
						Iterator<Delimiter> itr = compositeElementDelimiters.iterator();
						boolean isIncorrectCompDelim = true;
						while(itr.hasNext()) {
							Delimiter delim = itr.next();
							if(delim.getDelimiter().equals(items.get(18).trim())) {
								tpp.setCompositeElementDelimiter(delim);
								isIncorrectCompDelim = false;
							}
						}
						if(isIncorrectCompDelim) {
							logger.error(AppConstants.errorCode33);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode33);
							return tppReturnMsgBean1;									
						}
						//}
					}
				}

				if(items.get(19) != null && items.get(19).trim().length() != 0) {
					if(!(tpp.getRepeatDelimiter().getDelimiter()).equals(items.get(19).trim())) {
						/*						if(tpp.getCompositeElementDelimiter().getDelimiter().equals(items.get(19).trim()) || tpp.getElementDelimiter().getDelimiter().equals(items.get(19).trim()) || tpp.getSegmentDelimiter().getDelimiter().equals(items.get(19).trim())) {
							logger.error(AppConstants.errorCode29);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode29);
							return tppReturnMsgBean1;
						} else {*/
						List<Delimiter> repeatDelimiters = masterDataService.getRepeatDeleimiters();
						Iterator<Delimiter> itr = repeatDelimiters.iterator();
						boolean isIncorrectRepeatDelim = true;
						while(itr.hasNext()) {
							Delimiter delim = itr.next();
							if(delim.getDelimiter().equals(items.get(19).trim())) {
								tpp.setRepeatDelimiter(delim);
								isIncorrectRepeatDelim = false;
							}
						}
						if(isIncorrectRepeatDelim) {
							logger.error(AppConstants.errorCode34);
							tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode34);
							return tppReturnMsgBean1;										
						}
						//}
					} 
				} else if((items.get(19) == null || items.get(19).trim().length() == 0) && delimCount == 3) {
					tpp.setRepeatDelimiter(null);
				}
				//========================================================================================

				//ContactDetails
				//========================================================================================
				boolean isEmptyContact = false;
				StringBuffer sbMsg = new StringBuffer("");
				int j = 0;
				if(items.get(20) == null || items.get(20).trim().length() == 0) {
					j++;
					sbMsg.append(AppConstants.errorCode27);
					//logger.error(AppConstants.errorCode21);
				} 
				if(items.get(31) == null || items.get(31).trim().length() == 0) {
					j++;
					sbMsg.append(AppConstants.errorCode08);
				} 
				if(items.get(33) == null || items.get(33).trim().length() == 0) {
					j++;
					sbMsg.append(AppConstants.errorCode09);
				}					
				if(j == 1 || j == 2) {
					tppReturnMsgBean1.setReturnMsg(sbMsg.toString());
					logger.error(sbMsg.toString());
					return tppReturnMsgBean1;
				}
				if(j == 0) {
					ContactDetail contactDetail = seedDataService.getContactDetailFromCsvString(items.get(20)+","+items.get(21)+","+items.get(22)+","+items.get(23)+","+items.get(24)+","+items.get(25)+","+items.get(26)+","+items.get(27)+","+items.get(28)+","+items.get(29)+","+items.get(30)+","+items.get(31)+","+items.get(32)+","+items.get(33)+",");
					Set<ContactDetail> existingSet = tpp.getContactDetails();
					if(!isDuplicateTpp) {
						tpp.removeContactDetails(existingSet);
					} 					
					tpp.addContact(contactDetail);
					logger.debug("<============================================================>");
					logger.debug("Contact Size for Tpp: " + tpp.getContactDetails().size());
					logger.debug("<============================================================>");
				} 

				if(j == 3) {
					for(int m = 21; m <= 30 ; m++) {
						if(items.get(m) != null && items.get(m).trim().length() != 0) {
							tppReturnMsgBean1.setReturnMsg(sbMsg.toString());
							logger.error(sbMsg.toString());
							return tppReturnMsgBean1;
						}
					}
					if(items.get(32) != null && items.get(32).trim().length() != 0) {
						tppReturnMsgBean1.setReturnMsg(sbMsg.toString());
						logger.error(sbMsg.toString());
						return tppReturnMsgBean1;
					}
				}
				//========================================================================================
				tppReturnMsgBean1.setTpp(tppService.saveTpp(tpp));
				tppReturnMsgBean1.setReturnFlag(1);
			}
			//}
		} else {
			tppReturnMsgBean1.setReturnMsg(AppConstants.errorCode03);
			logger.error(AppConstants.errorCode03);
		}

		if(tppReturnMsgBean1.getReturnFlag() == 2 && tppReturnMsgBean1.getReturnMsg().length() == 0) {
			tppReturnMsgBean1.setReturnMsg("Nothing to update.");
		}
		return tppReturnMsgBean1;

	}

	@Override
	public SeedDataInsertStatMsg performUploadAbcIdMaintenance(String rowLine) {

		return loadIDMaintenance(rowLine);
	}


	@Override
	public SeedDataInsertStatMsg performUploadThroughAPI(String rowLine, String entityName) {

		logger.debug("Invoked performUploadThroughAPI().");

		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();

		String entityNameUpper = entityName.toUpperCase();
		logger.debug("Processing data for : " + entityNameUpper); 

		switch (entityNameUpper) 
		{
		case "ACK":  
			if (loadACK(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;
		case "DELIMITER":  
			if (loadDelimiter(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;
		case "DIRECTION":  
			if (loadDirection(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;						
		case "DOCUMENT":  
			if (loadDocument(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;
		case "MAP":
			if (loadMap(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;	
		case "PARTNER_TYPE":  
			if (loadPartnerType(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;					
		case "PROTOCOL":  
			if (loadProtocol(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;
		case "TPP_TYPE":  
			if (loadTppType(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;
		case "VERSION":  
			if (loadVersion(rowLine))
			{
				seedDataInsertStatMsg.setReturnFlag(0);
			}
			else
			{
				seedDataInsertStatMsg.setReturnFlag(1);
			}
			break;
		case "ABCIDMAINTENANCE":  
			seedDataInsertStatMsg = loadIDMaintenance(rowLine);
			break; 
		case "COMPLIANCE_MAP":  
			seedDataInsertStatMsg = loadComplianceMap(rowLine);
			break; 
		case "SDSERVICEACCESS":  
			seedDataInsertStatMsg = loadServiceAccess(rowLine);
			break;
		case "SDBUSINESSSERVICE":  
			seedDataInsertStatMsg = loadBusinessService(rowLine);
			break;
		default:
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg("Incorrect Entity Name.");
			break;
		}


		return seedDataInsertStatMsg;
	}

	@Override
	public SeedDataInsertStatMsg makeApiCallForUploadToHigherEnv(String rowLine, String entityName) {

		logger.debug("Invoked makeApiCallForUploadToHigherEnv().");
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		EnvironmentInfo envInfo = environmentService.findEnvironmentInfoByParamName("TRIGGER_API");
		logger.debug("EnvironmentInfo: " + envInfo);
		if(envInfo != null && envInfo.getParamVal().length() != 0 ) {
			logger.debug("URL for ABC ID Maintenance API: " + envInfo.getParamVal());

			try {
				String urlStr = envInfo.getParamVal() + entityName;
				URI urlPost = new URI(urlStr);

				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				List<MediaType> accept = new ArrayList();
				accept.add(MediaType.APPLICATION_JSON);
				headers.setAccept(accept);
				MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();  
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				body.add("rowLine", rowLine); 
				HttpEntity<?> requestProcStatus = new HttpEntity(body, headers); 
				logger.debug(requestProcStatus.toString());
				String response = restTemplate.postForObject(urlPost, requestProcStatus, String.class);
				logger.debug("Response from REST call: " + response);
				if(response != null) {
					ObjectMapper objectMapper = new ObjectMapper();					
					seedDataInsertStatMsg = objectMapper.readValue(response, new TypeReference<SeedDataInsertStatMsg>(){});
					logger.debug("Value of SeedDataInsertStatMsg: " + seedDataInsertStatMsg);
					if(seedDataInsertStatMsg != null) {
						return seedDataInsertStatMsg;
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				seedDataInsertStatMsg.setReturnFlag(2);
				seedDataInsertStatMsg.setStatusMsg("IOException");
				e.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				seedDataInsertStatMsg.setReturnFlag(2);
				seedDataInsertStatMsg.setStatusMsg("URISyntaxException");
				e1.printStackTrace();
			} catch (HttpClientErrorException e2) {
				// TODO Auto-generated catch block
				seedDataInsertStatMsg.setReturnFlag(2);
				seedDataInsertStatMsg.setStatusMsg("HttpClientErrorException");
				e2.printStackTrace();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				seedDataInsertStatMsg.setReturnFlag(2);
				seedDataInsertStatMsg.setStatusMsg("Exception");
				e3.printStackTrace();
			}
		}
		seedDataInsertStatMsg.setReturnFlag(2);
		seedDataInsertStatMsg.setStatusMsg("Error encountered during REST call.");
		return seedDataInsertStatMsg;
	}


	//Added by Pappu Prasad for Dynamic Data Load process - ServiceAccess Load
	private SeedDataInsertStatMsg loadServiceAccess(String line) {
		int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
		SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
		logger.debug("String line: " + line);
		SAReturnMsgBean saReturnMsgBean = seedDataService.getSAFromXlsxString(line);
		logger.debug("String saReturnMsgBean: " + saReturnMsgBean);

		if(saReturnMsgBean.getSdServiceAccess() == null) {
			seedDataInsertStatMsg.setReturnFlag(2);
			seedDataInsertStatMsg.setStatusMsg(saReturnMsgBean.getReturnMsg());
		} else {
			//<------------------Arindam Sikdar : Start of dataload in PCCE Schema implementation ------------------------->
			EnvironmentInfo envInfo = environmentService.findEnvironmentInfoByParamName("READONLY_DB");

			//<----------------------------------------START: STEP#1 Insert in PCCE Schema in same environment---------------------------------------->
			if(envInfo != null) {
				PcceAbcBusinessUnitLookup pcceAbcBusinessUnitLookup = new PcceAbcBusinessUnitLookup();

				SdServiceCategoryDef sdServiceCatDef = sdServiceCategoryService.findSdServiceCategoryById(saReturnMsgBean.getSdServiceAccess().getServiceCategoryDef().getId());
				pcceAbcBusinessUnitLookup.setBusinessUnit(sdServiceCatDef.getBusinessUnit().getName());
				pcceAbcBusinessUnitLookup.setSubBusinessUnit(sdServiceCatDef.getBusinessSubUnit().getSubUnitName());
				pcceAbcBusinessUnitLookup.setServicePreamble(saReturnMsgBean.getSdServiceAccess().getServicePreamble());
				if(saReturnMsgBean.getSdServiceAccess().getDestinationId() != null) {
					LightWellPartner lwp = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,saReturnMsgBean.getSdServiceAccess().getDestinationId().trim(),null);
					if(lwp != null) {
						if(envInfo != null || envInfo.getParamVal().equalsIgnoreCase("Y")) {
							pcceAbcBusinessUnitLookup.setDestinationId(lwp.getProductionGsId());
						} else {
							pcceAbcBusinessUnitLookup.setDestinationId(lwp.getTestGsId());
						}
					}
				} 
				if(saReturnMsgBean.getSdServiceAccess().getSourceId() != null) {
					LightWellPartner lwp = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,saReturnMsgBean.getSdServiceAccess().getSourceId().trim(),null);
					if(lwp != null) {
						if(envInfo != null || envInfo.getParamVal().equalsIgnoreCase("Y")) {
							pcceAbcBusinessUnitLookup.setSourceId(lwp.getProductionGsId());
						} else {
							pcceAbcBusinessUnitLookup.setSourceId(lwp.getTestGsId());
						}
					}
				} 
				pcceAbcBusinessUnitLookupService.savePcceAbcBusinessUnitLookup(pcceAbcBusinessUnitLookup);
			}
			//<----------------------------------------------Arindam Sikdar: End of dataload in PCCE Schema implementation ------------------------------>
			seedDataInsertStatMsg.setReturnFlag(saReturnMsgBean.getReturnFlag());
		}
		return seedDataInsertStatMsg;

	}
	//Added by Pappu Prasad for Dynamic Data Load process - ServiceAccess Load
	
	
	//Added by Manoj Kumar Dwari for Dynamic Data Load process - BusinessService Load
private SeedDataInsertStatMsg   loadBusinessService(String line) {
			int returnFlag = 0; //0 - INSERT, 1 - UPDATE, 2 - ERROR
			SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
			//SeedDataInsertStatMsg seedDataInsertStatMsg ;
			try {
				seedDataInsertStatMsg = sdSeedDataService.loadSdServiceCategory(line);
			} catch (TpiValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug("String line: " + line);
			
			if(seedDataInsertStatMsg.getSdBusinessService().getId() == null) {
					seedDataInsertStatMsg.setReturnFlag(2);
					logger.debug("inside If block  seedDataInsertStatMsg.getSdBusinessService() is null");
					} else {
					
					EnvironmentInfo envInfo = environmentService.findEnvironmentInfoByParamName("READONLY_DB");
					//<----------------------------------------START: STEP#1 Insert in PCCE Schema in Higher environment---------------------------------------->
					if(envInfo != null) {
						logger.debug("Environment info: " + envInfo.getParamVal().toString());
						SdBusinessService sdBusinessService = sdServiceCategoryService.findBusinessServiceById(seedDataInsertStatMsg.getSdBusinessService().getId());
						PcceAbcBusinessService businessService = new PcceAbcBusinessService();
						SdServiceCategoryDef sdServiceCatDef = sdServiceCategoryService.findSdServiceCategoryById(sdBusinessService.getServiceCategory().getId());
						ServiceCategory serviceCategory = sdServiceCatDef.getServiceCategory();
						SdBusinessUnit sdBusinessUnit = sdServiceCatDef.getBusinessUnit();
						SdBusinessSubUnit sdBusinessSubUnit = sdServiceCatDef.getBusinessSubUnit();
						Document document = masterDataService.findDocumentTypeById(sdBusinessService.getDocument().getId());
						Direction direction = masterDataService.findDirectionById(sdBusinessService.getDirection().getId());
						SdServiceType sdServiceType = sdMasterDataService.findServiceTypeById(sdBusinessService.getServiceType().getId());
						businessService.setServiceKey(sdBusinessService.getServiceKey());
						logger.debug("Service key is : " + businessService.getServiceKey());
						businessService.setServiceCategory(serviceCategory.getName());
						businessService.setCategoryId(Long.toString(serviceCategory.getId()));
						businessService.setServiceLevel(sdBusinessService.getServiceLevel());
						businessService.setServiceLevelId(sdBusinessService.getServiceLevelId());
						businessService.setBusinessUnit(sdBusinessUnit.getName());
						businessService.setBusinessSubUnit(sdBusinessSubUnit.getSubUnitName());
						businessService.setServicePreamble(sdBusinessService.getServicePreamble());
						businessService.setTransactionType(document.getDocumentDescription());
						businessService.setDirection(direction.getDirectionCode());
						businessService.setServiceType(sdServiceType.getName());
						businessService.setIntCompSendBu(sdBusinessSubUnit.getSubUnitName());
						businessService.setServiceDesc(sdBusinessService.getServiceDescription());
						pcceAbcBusinessLookupService.savePcceAbcBusinessService(businessService);
						logger.debug("after calling savePcceAbcBusinessService() method: ");
						//<----------------------------------------END: STEP#1---------------------------------------------------------------------->	
					}
				}
			return seedDataInsertStatMsg;
}

	/*	ServiceAccess Template Details
					----------------------------
					Col	Header
					0: SERVICE CATEGORY	
					1: SERVICE TYPE	
					2: SOURCE ID	
					3: DESTINATION ID	
					4: BUSINESS UNIT	
					5: BUSINESS SUB UNIT	
					6: SERVICE PREAMBLE	

	 */
	@Override
	public SAReturnMsgBean getSAFromXlsxString(String saString) {
		// TODO Auto-generated method stub

		SAReturnMsgBean saReturnMsgBean = new SAReturnMsgBean();
		saReturnMsgBean.setReturnMsg("");
		saReturnMsgBean.setReturnFlag(2);

		List<String> items = Arrays.asList(saString.split("\\s*,\\s*",-1));

		logger.debug("Inside getSAFromXlsxString() method.");
		logger.debug("Column Size: " + items.size());
		if(items.size() == 8) {
			if(items.get(0) == null || items.get(0).trim().length() == 0) {
				saReturnMsgBean.setReturnMsg(AppConstants.errorCode39);
				logger.error(AppConstants.errorCode39);
				return saReturnMsgBean;
			} 
			if(items.get(1) == null || items.get(1).trim().length() == 0) {
				saReturnMsgBean.setReturnMsg(AppConstants.errorCode64);
				logger.error(AppConstants.errorCode64);
				return saReturnMsgBean;
			} 
			if(items.get(4) == null || items.get(4).trim().length() == 0) {
				saReturnMsgBean.setReturnMsg(AppConstants.errorCode65);
				logger.error(AppConstants.errorCode65);
				return saReturnMsgBean;
			}
			if(items.get(5) == null || items.get(5).trim().length() == 0) {
				saReturnMsgBean.setReturnMsg(AppConstants.errorCode66);
				logger.error(AppConstants.errorCode66);
				return saReturnMsgBean;
			} 
			if(items.get(6) == null || items.get(6).trim().length() == 0) {
				saReturnMsgBean.setReturnMsg(AppConstants.errorCode67);
				logger.error(AppConstants.errorCode67);
				return saReturnMsgBean;
			} 


			SdServiceAccess sdServiceAccess = new SdServiceAccess();
			SdServiceCategoryDef sdServiceCategoryDef = sdServiceCategoryService.findOneSdServiceCategoryByServiceCategory(items.get(0).trim());
			if(sdServiceCategoryDef.getServiceCategory() == null) {
				saReturnMsgBean.setReturnMsg(AppConstants.errorCode40);
				logger.error(AppConstants.errorCode40);
				return saReturnMsgBean;
			}else{

				SdBusinessUnit sdBusinessUnit = sdMasterDataService.findSdBusinessUnitByName(items.get(4).trim());
				SdBusinessUnit sdBusinessUnitSub = sdMasterDataService.findSdBusinessUnitByNameAndSubUnitName(items.get(4).trim(), items.get(5).trim());
				if(sdBusinessUnit == null)
				{
					saReturnMsgBean.setReturnMsg(AppConstants.errorCode69);
					logger.error(AppConstants.errorCode69);
					return saReturnMsgBean;

				}else if(sdBusinessUnitSub == null) {
					saReturnMsgBean.setReturnMsg(AppConstants.errorCode70);
					logger.error(AppConstants.errorCode70);
					return saReturnMsgBean;
				}else if(sdBusinessUnitSub.getName().equalsIgnoreCase(sdServiceCategoryDef.getBusinessUnit().getName())){

					logger.debug("SdBusinessUnit :"+sdBusinessUnitSub.getName());
					Set<SdBusinessSubUnit> sbuset = sdBusinessUnit.getSubUnits();
					Iterator<SdBusinessSubUnit> sbuitr =  sbuset.iterator();

					while(sbuitr.hasNext()){
						SdBusinessSubUnit sbu = sbuitr.next();
						if(sbu.getSubUnitName().equalsIgnoreCase(items.get(5).trim())){
							//sdSCDef.setBusinessUnit(sdServiceCategoryDef.getBusinessUnit());
							//sdSCDef.setBusinessSubUnit(sdServiceCategoryDef.getBusinessSubUnit());	
							//sdSCDef.setServiceCategory(sdServiceCategoryDef.getServiceCategory());
							sdServiceAccess.setServiceCategoryDef(sdServiceCategoryDef);
							//sdServiceCategoryService.saveSdServiceCategoryDef(sdSCDef);
						}

					}
					if(sdServiceCategoryDef.getBusinessSubUnit()==null){
						logger.debug("Sub Business unit of data load sheet is not matching with Sub Business unit of Service category");
						saReturnMsgBean.setReturnMsg(AppConstants.errorCode70);
						logger.error(AppConstants.errorCode70);
						return saReturnMsgBean;
					}

				}
				else
				{
					logger.debug("Business unit of data load sheet is not matching with Business unit of Service category");
					saReturnMsgBean.setReturnMsg(AppConstants.errorCode69);
					logger.error(AppConstants.errorCode69);
					return saReturnMsgBean;

				}
				//List<LightWellPartner>  lwplistic = sdServiceCategoryService.getLightWellPartnersWithSdServiceCategoryMembership();

				ServiceCategory sc = sdServiceCategoryDef.getServiceCategory();
				Set<LightWellPartner>  lwplist = sc.getLightWellPartners();
				logger.debug("LightWellPartner set :"+lwplist);

				SdServiceType sdServiceType = sdMasterDataService.findServiceTypeByName(items.get(1).trim());
				if(sdServiceType.getName() == null){
					saReturnMsgBean.setReturnMsg(AppConstants.errorCode68);
					logger.error(AppConstants.errorCode68);
					return saReturnMsgBean;
				}else if(sdServiceType.getName().equalsIgnoreCase("Intercompany")){
					if(items.get(2) == null || items.get(2).trim().length() == 0) {
						saReturnMsgBean.setReturnMsg(AppConstants.errorCode75);
						logger.error(AppConstants.errorCode75);
						return saReturnMsgBean;
					}
					if(items.get(3) == null || items.get(3).trim().length() == 0) {
						saReturnMsgBean.setReturnMsg(AppConstants.errorCode76);
						logger.error(AppConstants.errorCode76);
						return saReturnMsgBean;
					} 

					if(sdServiceCategoryDef.getBusinessSubUnit() != null){
						//sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit()+"2"+sdServiceCategoryDef.getBusinessSubUnit());
						sdServiceAccess.setServicePreamble(items.get(6).trim());
						sdServiceAccess.setServiceType(sdServiceType);
					}
					// set Did and Did from lightwellpartner Prod GS id
					if(lwplist != null){
						for (LightWellPartner ptnr : lwplist)
						{
							logger.debug("lightwellpartner Prod GS id :"+ptnr.getProductionGsId());
							if(ptnr.getProductionGsId().equalsIgnoreCase(items.get(2).trim()))
							{  
								logger.debug("lightwellpartner Prod GS id :"+ptnr.getProductionGsId()+"Dataload Source Id:"+items.get(2).trim());
								sdServiceAccess.setSourceId(ptnr.getProductionGsId());
							}if(ptnr.getProductionGsId().equalsIgnoreCase(items.get(3).trim())){
								if(items.get(2).trim().equalsIgnoreCase(items.get(3).trim())){
									saReturnMsgBean.setReturnMsg(AppConstants.errorCode74);
									logger.error(AppConstants.errorCode74);
									return saReturnMsgBean;
								}else{

									sdServiceAccess.setDestinationId(ptnr.getProductionGsId());
									//List<String> lt = sdMasterDataService.findSubUnitForABCid(ptnr.getProductionGsId());
									//logger.debug("1st sub unit for service preamble============="+lt.listIterator().next());
									//sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit().getSubUnitName().toString()+"2"+lt.listIterator().next());
									/*
							for (String str : lt)
							{
								logger.debug("sub unit for service preamble============="+str);
								sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit().getSubUnitName().toString()+"2"+str);
							}	*/
								}
							}

						}
						if(sdServiceAccess.getSourceId()==null){
							saReturnMsgBean.setReturnMsg(AppConstants.errorCode73);
							logger.error(AppConstants.errorCode73);
							return saReturnMsgBean;
						}
						if(sdServiceAccess.getDestinationId()==null){
							saReturnMsgBean.setReturnMsg(AppConstants.errorCode72);
							logger.error(AppConstants.errorCode72);
							return saReturnMsgBean;
						}


					}		// end of for loop

				}
				//}

				else if(sdServiceType.getName().equalsIgnoreCase("SM System Use")){
					sdServiceAccess.setServiceType(sdServiceType);
					sdServiceAccess.setServicePreamble(items.get(6).trim());

				}else if(sdServiceType.getName().equalsIgnoreCase("Customer/Standard IN")){
					logger.debug("sdServiceType name"+sdServiceType.getName());
					//sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit().getSubUnitName());
					sdServiceAccess.setServicePreamble(items.get(6).trim());
					sdServiceAccess.setServiceType(sdServiceType);
					if(lwplist != null){
						Iterator<LightWellPartner> lwpitr = lwplist.iterator();

						while(lwpitr.hasNext()){
							LightWellPartner lwp = lwpitr.next();
							logger.debug("LightWellPartner prod gs id:"+lwp.getProductionGsId());
							if(lwp.getProductionGsId().equalsIgnoreCase(items.get(3).trim()))
							{  

								sdServiceAccess.setDestinationId(lwp.getProductionGsId());
								// Sid not required
							}

						}
						if(sdServiceAccess.getDestinationId()==null){
							saReturnMsgBean.setReturnMsg(AppConstants.errorCode72);
							logger.error(AppConstants.errorCode72);
							return saReturnMsgBean;
						}
					}
				}else if(sdServiceType.getName().equalsIgnoreCase("Customer/Standard OUT")){
					logger.debug("sdServiceType name:"+sdServiceType.getName());
					//sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit().getSubUnitName());
					sdServiceAccess.setServicePreamble(items.get(6).trim());
					sdServiceAccess.setServiceType(sdServiceType);
					if(lwplist != null){
						Iterator<LightWellPartner> lwpitr = lwplist.iterator();
						while(lwpitr.hasNext()){

							LightWellPartner lwp = lwpitr.next();
							if(lwp.getProductionGsId().equalsIgnoreCase(items.get(2).trim()))
							{
								sdServiceAccess.setSourceId(lwp.getProductionGsId());
							}
						}
						if(sdServiceAccess.getSourceId()==null){
							saReturnMsgBean.setReturnMsg(AppConstants.errorCode73);
							logger.error(AppConstants.errorCode73);
							return saReturnMsgBean;
						}
					}
				}else if(sdServiceType.getName().equalsIgnoreCase("Customer/Special IN")){
					sdServiceAccess.setServiceType(sdServiceType);
					sdServiceAccess.setServicePreamble(items.get(6).trim());
					logger.debug("sdServiceType.getName:"+sdServiceType.getName());
					if(lwplist != null){

						for (LightWellPartner ptnr : lwplist)
						{
							logger.debug("LightWellPartner prod gs id:"+ptnr.getProductionGsId());
							if((!items.get(2).isEmpty()) && (!items.get(3).isEmpty())){
								logger.debug("inside if block===:");
								if(items.get(2).trim().equalsIgnoreCase(items.get(3).trim())){
									saReturnMsgBean.setReturnMsg(AppConstants.errorCode74);
									logger.error(AppConstants.errorCode74);
									return saReturnMsgBean;
								}else if(ptnr.getProductionGsId().equalsIgnoreCase(items.get(3).trim())){
									sdServiceAccess.setSourceId(items.get(2).trim());
									sdServiceAccess.setDestinationId(ptnr.getProductionGsId());
								}
								if(sdServiceAccess.getDestinationId()==null){
									saReturnMsgBean.setReturnMsg(AppConstants.errorCode72);
									logger.error(AppConstants.errorCode72);
									return saReturnMsgBean;
								}
							}
						}

					}
				}else if(sdServiceType.getName().equalsIgnoreCase("Supplier/Standard IN")){
					sdServiceAccess.setServicePreamble(items.get(6).trim());
					//sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit().getSubUnitName());
					sdServiceAccess.setServiceType(sdServiceType);
					if(lwplist != null){
						for (LightWellPartner ptnr : lwplist)
						{
							logger.debug("LightWellPartner prod gs id:"+ptnr.getProductionGsId());
							if(ptnr.getProductionGsId().equalsIgnoreCase(items.get(3).trim()))
							{  
								//logger.debug("Prod gs id :"+ptnr.getProductionGsId()+items.get(3).trim());
								sdServiceAccess.setDestinationId(ptnr.getProductionGsId());
								// sid not required
							}
						}
						if(sdServiceAccess.getDestinationId()==null){
							saReturnMsgBean.setReturnMsg(AppConstants.errorCode72);
							logger.error(AppConstants.errorCode72);
							return saReturnMsgBean;
						}
					}
				}else if(sdServiceType.getName().equalsIgnoreCase("Supplier/Standard OUT")){
					sdServiceAccess.setServicePreamble(items.get(6).trim());
					//sdServiceAccess.setServicePreamble(sdServiceCategoryDef.getBusinessSubUnit().getSubUnitName());
					sdServiceAccess.setServiceType(sdServiceType);
					if(lwplist != null){
						for (LightWellPartner ptnr : lwplist)
						{
							logger.debug("LightWellPartner Prod gs id:"+ptnr.getProductionGsId());
							if(ptnr.getProductionGsId().equalsIgnoreCase(items.get(2).trim()))
							{  
								logger.debug("LightWellPartner id:"+ptnr.getProductionGsId()+items.get(2).trim());
								sdServiceAccess.setSourceId(ptnr.getProductionGsId());
								// sid not required
							}
						}
						if(sdServiceAccess.getSourceId()==null){
							saReturnMsgBean.setReturnMsg(AppConstants.errorCode73);
							logger.error(AppConstants.errorCode73);
							return saReturnMsgBean;
						}
					}
				}else if(sdServiceType.getName().equalsIgnoreCase("Universal/Standard")){
					logger.debug("sdServiceType :"+sdServiceType.getName());
					sdServiceAccess.setServiceType(sdServiceType);
					sdServiceAccess.setServicePreamble(items.get(6).trim());

				}
				logger.debug("sdServiceAccess===:"+sdServiceAccess);
				if(sdServiceAccess != null){
					sdServiceAccess = sdServiceCategoryService.saveSdServiceAccess(sdServiceAccess);
					saReturnMsgBean.setSdServiceAccess(sdServiceAccess);
					saReturnMsgBean.setReturnFlag(0);
				}

			}// end of else for service category
		} else {
			saReturnMsgBean.setReturnMsg(AppConstants.errorCode03);
			logger.error(AppConstants.errorCode03);
		}
		if(saReturnMsgBean.getReturnFlag() == 2 && saReturnMsgBean.getReturnMsg().length() == 0) {
			saReturnMsgBean.setReturnMsg("Nothing to update.");
		}
		return saReturnMsgBean;
	}
}
