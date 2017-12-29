package com.abc.tpi.view.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.abc.tpi.controller.PromotionController;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.partner.ContactDetail;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.promotion.ServiceSubscriptionPromotion;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.Transaction;

public class PromotionExportXlsx extends TpiXlsxReport {
	
	private static final Logger logger = LogManager.getLogger(PromotionExportXlsx.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "PromotionExport.xlsx";

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName );

		String firstTabName = "Partner";
		String secondTabName = "3PP";
		String thirdTabName = "ServiceSubscription";

		@SuppressWarnings("unchecked")
		List<Partner> partnerRecords = (List<Partner>) model.get("partners");

		@SuppressWarnings("unchecked")
		List<Tpp> tppRecords = (List<Tpp>) model.get("tpps");

		@SuppressWarnings("unchecked")
		List<ServiceSubscriptionPromotion> srvcSubscriptionRecords = (List<ServiceSubscriptionPromotion>) model.get("serviceSubscription");

		// excel tab name cannot exceed 30 characters
		if (firstTabName.length() >= 30) {
			firstTabName = firstTabName.substring(0, 29);
		}
		if (secondTabName.length() >= 30) {
			secondTabName = secondTabName.substring(0, 29);
		}
		if (thirdTabName.length() >= 30) {
			thirdTabName = thirdTabName.substring(0, 29);
		}

		// create excel XLS sheet
		Sheet firstSheet = workbook.createSheet(firstTabName);

		/*		Partner Name
		Partner Group Name
		Partner Sub Group Name
		Contact Name
		TITLE
		BUSINESS COUNTRY
		BUSINESS PHONE
		BUSINESS EXT
		MOBILE COUNTRY
		MOBILE PHONE
		MOBILE EXT
		PERSONAL COUNTRY
		PERSONAL PHONE
		PERSONAL EXT
		Primary Email
		Secondary Email
		Transaction Type*/

		String[] rowHeadersForFirstTab = { 
				"Partner Name", 
				"Partner Group Name", 
				"Partner Sub Group Name", 
				"Contact Name",
				"TITLE", 
				"BUSINESS COUNTRY", 
				"BUSINESS PHONE",
				"BUSINESS EXT",
				"MOBILE COUNTRY",
				"MOBILE PHONE",
				"MOBILE EXT",
				"PERSONAL COUNTRY",
				"PERSONAL PHONE",
				"PERSONAL EXT",
				"Primary Email",
				"Secondary Email",
				"Transaction Type"						

		};

		addHeaderRow(firstSheet, rowHeadersForFirstTab);

		if(partnerRecords != null && !partnerRecords.isEmpty()) {
			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for(Partner partner : partnerRecords) {
				Set<ContactDetail> contactDetails = partner.getContactDetails();

				for(ContactDetail cDetail : contactDetails) {
					cellValuesArray = new String[17];
					cellValuesArray[0] = partner.getPartnerName();
					cellValuesArray[1] = partner.getPartnerGroup().getGroupName();
					cellValuesArray[2] = partner.getPartnerGroup().getSubGroupName();
					cellValuesArray[3] = cDetail.getContactName();
					if(cDetail.getContactTitle() != null) {
						cellValuesArray[4] = cDetail.getContactTitle();
					} else {
						cellValuesArray[4] = "";
					}

					if(cDetail.getBusinessPhoneCountry() != null) {
						cellValuesArray[5] = cDetail.getBusinessPhoneCountry();
					} else {
						cellValuesArray[5] = "USA";
					}

					if(cDetail.getBusinessPhone() != null) {
						cellValuesArray[6] = cDetail.getBusinessPhone();
					} else {
						cellValuesArray[6] = "";
					}

					if(cDetail.getBusinessPhoneExt() != null) {
						cellValuesArray[7] = cDetail.getBusinessPhoneExt();
					} else {
						cellValuesArray[7] = "";
					}

					if(cDetail.getMobilePhoneCountry() != null) {
						cellValuesArray[8] = cDetail.getMobilePhoneCountry();
					} else {
						cellValuesArray[8] = "USA";
					}

					if(cDetail.getMobilePhone() != null) {
						cellValuesArray[9] = cDetail.getMobilePhone();
					} else {
						cellValuesArray[9] = "";
					}

					if(cDetail.getMobilePhoneExt() != null) {
						cellValuesArray[10] = cDetail.getMobilePhoneExt();
					} else {
						cellValuesArray[10] = "";
					}

					if(cDetail.getPersonalPhoneCountry() != null) {
						cellValuesArray[11] = cDetail.getPersonalPhoneCountry();
					} else {
						cellValuesArray[11] = "USA";
					}

					if(cDetail.getPersonalPhone() != null) {
						cellValuesArray[12] = cDetail.getPersonalPhone();
					} else {
						cellValuesArray[12] = "";
					}

					if(cDetail.getPersonalPhoneExt() != null) {
						cellValuesArray[13] = cDetail.getPersonalPhoneExt();
					} else {
						cellValuesArray[13] = "";
					}

					cellValuesArray[14] = cDetail.getContactEmail();
					if(cDetail.getContactEmail2() != null) {
						cellValuesArray[15] = cDetail.getContactEmail2();
					} else {
						cellValuesArray[15] = "";
					}

					cellValuesArray[16] = cDetail.getTransactionType().getDocumentType();

					cellValues.add(cellValuesArray);
				}

			}
			addRow(firstSheet, cellValues);
		}

		// create excel XLS sheet
		Sheet secondSheet = workbook.createSheet(secondTabName);


		String[] rowHeadersForSecondTab = { 
				"3PP NAME",
				"3PP TYPE CODE",	
				"TEST ISA ID",	
				"TEST ISA QUAL",	
				"TEST GS ID",	
				"PROD ISA ID",	
				"PROD ISA QUAL",	
				"PROD GS ID",	
				"PROTOCOL#1",	
				"PROTOCOL#2",	
				"PROTOCOL#3",	
				"PROTOCOL#4",	
				"PROTOCOL#5",	
				"TRANSACTION DIRECTION",	
				"TRANSACTION TYPE",	
				"TRANSACTION VERSION",	
				"SEGMENT DELIMITER",	
				"ELEMENT DELIMITER",	
				"COMPOSITE DELIMITER",	
				"REPEAT DELIMITER",	
				"CONTACT NAME",	
				"TITLE",	
				"BUSINESS COUNTRY",	
				"BUSINESS PHONE",	
				"BUSINESS EXT",	
				"MOBILE COUNTRY",	
				"MOBILE PHONE",	
				"MOBILE EXT",	
				"PERSONAL COUNTRY",	
				"PERSONAL PHONE",	
				"PERSONAL EXT",	
				"Primary Email",	
				"Secondary Email",	
				"Transaction Type"					

		};

		addHeaderRow(secondSheet, rowHeadersForSecondTab);

		if(tppRecords != null && !tppRecords.isEmpty()) {
			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for(Tpp tpp : tppRecords) {
				Set<ContactDetail> contactDetails = tpp.getContactDetails();
				Set<Transaction> transactions = tpp.getTransactions();
				logger.debug("Size of Contacts: " + contactDetails.size());
				logger.debug("Size of Transactions: " + transactions.size());
				
				for(ContactDetail cDetail : contactDetails) {
					for(Transaction transaction : transactions) {
						cellValuesArray = new String[34];
						cellValuesArray[0] = tpp.getName();
						cellValuesArray[1] = tpp.getType().getTypeCode().toString();
						if(tpp.getType().getTypeCode() == Short.valueOf("1")) {
							cellValuesArray[2] = tpp.getLightWellPartner().getTestIsaID();
							cellValuesArray[3] = tpp.getLightWellPartner().getTestIsaQualifier();
							cellValuesArray[4] = tpp.getLightWellPartner().getTestGsId();
							cellValuesArray[5] = tpp.getLightWellPartner().getProductionIsaID();
							cellValuesArray[6] = tpp.getLightWellPartner().getProductionIsaQualifier();
							cellValuesArray[7] = tpp.getLightWellPartner().getProductionGsId();
						} else {
							cellValuesArray[2] = "";
							cellValuesArray[3] = "";
							cellValuesArray[4] = "";
							cellValuesArray[5] = "";
							cellValuesArray[6] = "";
							cellValuesArray[7] = "";
						}
						Set<Protocol> protocols = tpp.getProtocols();
						if(protocols.size() == 1) {
							for(Protocol protocol : protocols) {
								cellValuesArray[8] = protocol.getProtocolType();
							}
							cellValuesArray[9] = "";
							cellValuesArray[10] = "";
							cellValuesArray[11] = "";
							cellValuesArray[12] = "";
						} else {
							int i = 8;
							for(Protocol protocol : protocols) {
								cellValuesArray[i] = protocol.getProtocolType();
								i++;
							}
							for( ;i <= 12; i++) {
								cellValuesArray[i] = "";
							}
						}

						cellValuesArray[13] = transaction.getDirection().getDirectionCode();
						cellValuesArray[14] = transaction.getDocument().getDocumentType();
						cellValuesArray[15] = transaction.getVersion().getVersionNumber().toString();
						cellValuesArray[16] = tpp.getSegmentDelimiter().getDelimiter();
						cellValuesArray[17] = tpp.getElementDelimiter().getDelimiter();
						cellValuesArray[18] = tpp.getCompositeElementDelimiter().getDelimiter();
						if(tpp.getRepeatDelimiter() != null) {
							cellValuesArray[19] = tpp.getRepeatDelimiter().getDelimiter();
						} else {
							cellValuesArray[19] = "";
						}
						cellValuesArray[20] = cDetail.getContactName();
						if(cDetail.getContactTitle() != null) {
							cellValuesArray[21] = cDetail.getContactTitle();
						} else {
							cellValuesArray[21] = "";
						}

						if(cDetail.getBusinessPhoneCountry() != null) {
							cellValuesArray[22] = cDetail.getBusinessPhoneCountry();
						} else {
							cellValuesArray[22] = "USA";
						}

						if(cDetail.getBusinessPhone() != null) {
							cellValuesArray[23] = cDetail.getBusinessPhone();
						} else {
							cellValuesArray[23] = "";
						}

						if(cDetail.getBusinessPhoneExt() != null) {
							cellValuesArray[24] = cDetail.getBusinessPhoneExt();
						} else {
							cellValuesArray[24] = "";
						}

						if(cDetail.getMobilePhoneCountry() != null) {
							cellValuesArray[25] = cDetail.getMobilePhoneCountry();
						} else {
							cellValuesArray[25] = "USA";
						}

						if(cDetail.getMobilePhone() != null) {
							cellValuesArray[26] = cDetail.getMobilePhone();
						} else {
							cellValuesArray[26] = "";
						}

						if(cDetail.getMobilePhoneExt() != null) {
							cellValuesArray[27] = cDetail.getMobilePhoneExt();
						} else {
							cellValuesArray[27] = "";
						}

						if(cDetail.getPersonalPhoneCountry() != null) {
							cellValuesArray[28] = cDetail.getPersonalPhoneCountry();
						} else {
							cellValuesArray[28] = "USA";
						}

						if(cDetail.getPersonalPhone() != null) {
							cellValuesArray[29] = cDetail.getPersonalPhone();
						} else {
							cellValuesArray[29] = "";
						}

						if(cDetail.getPersonalPhoneExt() != null) {
							cellValuesArray[30] = cDetail.getPersonalPhoneExt();
						} else {
							cellValuesArray[30] = "";
						}

						cellValuesArray[31] = cDetail.getContactEmail();

						if(cDetail.getContactEmail2() != null) {
							cellValuesArray[32] = cDetail.getContactEmail2();
						} else {
							cellValuesArray[32] = "";
						}

						cellValuesArray[33] = cDetail.getTransactionType().getDocumentType();
						cellValues.add(cellValuesArray);
						break;
					}
					break;
				}

				if(contactDetails.size() > 1) {
					boolean isFirstRow = true;
					for(ContactDetail cDetail : contactDetails) {
						if(isFirstRow) {
							isFirstRow = false;
						} else {							
							cellValuesArray = new String[34];
							cellValuesArray[0] = tpp.getName();
							cellValuesArray[1] = tpp.getType().getTypeCode().toString();
							if(tpp.getType().getTypeCode() == Short.valueOf("1")) {
								cellValuesArray[2] = tpp.getLightWellPartner().getTestIsaID();
								cellValuesArray[3] = tpp.getLightWellPartner().getTestIsaQualifier();
								cellValuesArray[4] = tpp.getLightWellPartner().getTestGsId();
								cellValuesArray[5] = tpp.getLightWellPartner().getProductionIsaID();
								cellValuesArray[6] = tpp.getLightWellPartner().getProductionIsaQualifier();
								cellValuesArray[7] = tpp.getLightWellPartner().getProductionGsId();
							} else {
								cellValuesArray[2] = "";
								cellValuesArray[3] = "";
								cellValuesArray[4] = "";
								cellValuesArray[5] = "";
								cellValuesArray[6] = "";
								cellValuesArray[7] = "";
							}
							Set<Protocol> protocols = tpp.getProtocols();
							if(protocols.size() == 1) {
								for(Protocol protocol : protocols) {
									cellValuesArray[8] = protocol.getProtocolType();
								}
								cellValuesArray[9] = "";
								cellValuesArray[10] = "";
								cellValuesArray[11] = "";
								cellValuesArray[12] = "";
							} else {
								int i = 8;
								for(Protocol protocol : protocols) {
									cellValuesArray[i] = protocol.getProtocolType();
									i++;
								}
								for( ;i <= 12; i++) {
									cellValuesArray[i] = "";
								}
							}

							cellValuesArray[13] = "";
							cellValuesArray[14] = "";
							cellValuesArray[15] = "";
							cellValuesArray[16] = "";
							cellValuesArray[17] = "";
							cellValuesArray[18] = "";
							cellValuesArray[19] = "";

							cellValuesArray[20] = cDetail.getContactName();
							logger.debug("Contact Name: " + cellValuesArray[20]);
							
							if(cDetail.getContactTitle() != null) {
								cellValuesArray[21] = cDetail.getContactTitle();
							} else {
								cellValuesArray[21] = "";
							}

							if(cDetail.getBusinessPhoneCountry() != null) {
								cellValuesArray[22] = cDetail.getBusinessPhoneCountry();
							} else {
								cellValuesArray[22] = "USA";
							}

							if(cDetail.getBusinessPhone() != null) {
								cellValuesArray[23] = cDetail.getBusinessPhone();
							} else {
								cellValuesArray[23] = "";
							}

							if(cDetail.getBusinessPhoneExt() != null) {
								cellValuesArray[24] = cDetail.getBusinessPhoneExt();
							} else {
								cellValuesArray[24] = "";
							}

							if(cDetail.getMobilePhoneCountry() != null) {
								cellValuesArray[25] = cDetail.getMobilePhoneCountry();
							} else {
								cellValuesArray[25] = "USA";
							}

							if(cDetail.getMobilePhone() != null) {
								cellValuesArray[26] = cDetail.getMobilePhone();
							} else {
								cellValuesArray[26] = "";
							}

							if(cDetail.getMobilePhoneExt() != null) {
								cellValuesArray[27] = cDetail.getMobilePhoneExt();
							} else {
								cellValuesArray[27] = "";
							}

							if(cDetail.getPersonalPhoneCountry() != null) {
								cellValuesArray[28] = cDetail.getPersonalPhoneCountry();
							} else {
								cellValuesArray[28] = "USA";
							}

							if(cDetail.getPersonalPhone() != null) {
								cellValuesArray[29] = cDetail.getPersonalPhone();
							} else {
								cellValuesArray[29] = "";
							}

							if(cDetail.getPersonalPhoneExt() != null) {
								cellValuesArray[30] = cDetail.getPersonalPhoneExt();
							} else {
								cellValuesArray[30] = "";
							}

							cellValuesArray[31] = cDetail.getContactEmail();

							if(cDetail.getContactEmail2() != null) {
								cellValuesArray[32] = cDetail.getContactEmail2();
							} else {
								cellValuesArray[32] = "";
							}

							cellValuesArray[33] = cDetail.getTransactionType().getDocumentType();
							cellValues.add(cellValuesArray);
						}
					}
				}
				// for transaction
				if(transactions.size() > 1) {
					//logger.debug("More than 1 transactions.");
					boolean isFirstRow = true;
					for(Transaction transaction : transactions) {
						//logger.debug("isFirstRow: " + isFirstRow);
						if(isFirstRow) {
							isFirstRow = false;
						} else {
							
							cellValuesArray = new String[34];
							cellValuesArray[0] = tpp.getName();
							cellValuesArray[1] = tpp.getType().getTypeCode().toString();
							if(tpp.getType().getTypeCode() == Short.valueOf("1")) {
								cellValuesArray[2] = tpp.getLightWellPartner().getTestIsaID();
								cellValuesArray[3] = tpp.getLightWellPartner().getTestIsaQualifier();
								cellValuesArray[4] = tpp.getLightWellPartner().getTestGsId();
								cellValuesArray[5] = tpp.getLightWellPartner().getProductionIsaID();
								cellValuesArray[6] = tpp.getLightWellPartner().getProductionIsaQualifier();
								cellValuesArray[7] = tpp.getLightWellPartner().getProductionGsId();
							} else {
								cellValuesArray[2] = "";
								cellValuesArray[3] = "";
								cellValuesArray[4] = "";
								cellValuesArray[5] = "";
								cellValuesArray[6] = "";
								cellValuesArray[7] = "";
							}
							Set<Protocol> protocols = tpp.getProtocols();
							if(protocols.size() == 1) {
								for(Protocol protocol : protocols) {
									cellValuesArray[8] = protocol.getProtocolType();
								}
								cellValuesArray[9] = "";
								cellValuesArray[10] = "";
								cellValuesArray[11] = "";
								cellValuesArray[12] = "";
							} else {
								int i = 8;
								for(Protocol protocol : protocols) {
									cellValuesArray[i] = protocol.getProtocolType();
									i++;
								}
								for( ;i <= 12; i++) {
									cellValuesArray[i] = "";
								}
							}

							cellValuesArray[13] = transaction.getDirection().getDirectionCode();
							logger.debug("Transaction Direction: " + cellValuesArray[13]);
							cellValuesArray[14] = transaction.getDocument().getDocumentType();
							cellValuesArray[15] = transaction.getVersion().getVersionNumber().toString();
							cellValuesArray[16] = "";
							cellValuesArray[17] = "";
							cellValuesArray[18] = "";
							cellValuesArray[19] = "";							
							cellValuesArray[20] = "";
							cellValuesArray[21] = "";
							cellValuesArray[22] = "";
							cellValuesArray[23] = "";
							cellValuesArray[24] = "";
							cellValuesArray[25] = "";
							cellValuesArray[26] = "";
							cellValuesArray[27] = "";
							cellValuesArray[28] = "";
							cellValuesArray[29] = "";	
							cellValuesArray[30] = "";		
							cellValuesArray[31] = "";
							cellValuesArray[32] = "";		
							cellValuesArray[33] = "";
							cellValues.add(cellValuesArray);
						}
					}
				}
			}
			addRow(secondSheet, cellValues);
		}



		// create excel XLS sheet
		Sheet thirdSheet = workbook.createSheet(thirdTabName);
		/*
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

		String[] rowHeadersForThirdTab = { 
				"Partner Name", 
				"Segment Delimiter", 
				"Element Delimiter", 
				"Composite Delimiter",
				"Repeat Delimiter", 
				"Partner Test ISA ID", 
				"Partner Test ISA Qual",
				"Partner Test GS ID",
				"Partner Production ISA ID",
				"Partner Production ISA Qual",
				"Partner Production GS ID",
				"ABC GS Production ID",
				"ISA Version",
				"GS Version",
				"Protocol",
				"Ack Required?",
				"Ack Period",
				"ST Control Number",
				"ISA Control Number",
				"ST AcceptorLookUpAlias",
				"Compliance Required?",
				"Complaince Map Name:",
				"3PP Name",
				"Service Category",
				"Service Category Request ID",
				"Service Category Comments",
				"Business Service",
				"BusinessService Request ID",
				"BusinessService Comments"								

		};

		addHeaderRow(thirdSheet, rowHeadersForThirdTab);


		if (srvcSubscriptionRecords != null && !srvcSubscriptionRecords.isEmpty()) {

			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for (ServiceSubscriptionPromotion record : srvcSubscriptionRecords) {

				cellValuesArray = new String[29];
				cellValuesArray[0] = record.getPartnerName();
				cellValuesArray[1] = record.getSegmentDelimiter();
				cellValuesArray[2] = record.getElementDelimiter();
				cellValuesArray[3] = record.getCompositeElementDelimiter();				
				cellValuesArray[4] = record.getRepeatDelimiter();				
				cellValuesArray[5] = record.getTestIsaID();
				cellValuesArray[6] = record.getTestIsaQualifier();
				cellValuesArray[7] = record.getTestGsId();
				cellValuesArray[8] = record.getProductionIsaID();
				cellValuesArray[9] = record.getProductionIsaQualifier();
				cellValuesArray[10] = record.getProductionGsId();
				cellValuesArray[11] = record.getAbcProductionGsId();
				cellValuesArray[12] = record.getVersionNumber().toString();
				cellValuesArray[13] = record.getGsIdVersion();
				cellValuesArray[14] = record.getProtocolType();
				cellValuesArray[15] = record.getAck();
				cellValuesArray[16] = Integer.toString(record.getAckPeriod());
				cellValuesArray[17] = record.getStControlNum();
				cellValuesArray[18] = record.getIsaControlNum();
				cellValuesArray[19] = record.getStAcceptorLookUpAlias();
				cellValuesArray[20] = record.getComplianceCheck();
				cellValuesArray[21] = record.getMapName();
				cellValuesArray[22] = record.getTppName();
				cellValuesArray[23] = record.getServiceCategoryName();
				cellValuesArray[24] = record.getSrId();
				cellValuesArray[25] = record.getNotes();
				cellValuesArray[26] = record.getBusinessServiceName();
				cellValuesArray[27] = record.getBsSRId();
				cellValuesArray[28] = record.getBsNotes();			

				cellValues.add(cellValuesArray);
			}
			addRow(thirdSheet, cellValues);
		}					

	}
}
