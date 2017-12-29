package com.abc.tpi.view.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.abc.tpi.model.reporting.LightWellPartnerRecord;
import com.abc.tpi.model.reporting.PartnerRecord;

public class ExcelPartnerReport extends TpiXlsxReport{
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String fileName = "SamplePartnerDataloadTemplate.xlsx";
	
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName );

		String tabName = "TestPartner";

		@SuppressWarnings("unchecked")
		List<PartnerRecord> records = (List<PartnerRecord>) model.get("partnerRecord");

		// excel tab name cannot exceed 30 characters
		if (tabName.length() >= 30) {
			tabName = tabName.substring(0, 29);
		}

		// create excel XLS sheet
		Sheet sheet = workbook.createSheet(tabName);


		String[] rowHeaders = { "Partner Name", 
								"Partner Group Name", 
								"Partner Sub Group Name", 
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
								"Transaction Type",
							};
			
			addHeaderRow(sheet, rowHeaders);



		if (records != null && !records.isEmpty()) {

			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for (PartnerRecord record : records) {
				
				cellValuesArray = new String[17];
				cellValuesArray[0] = record.getPartnerName();
				cellValuesArray[1] = record.getPartnerGroupName();
				cellValuesArray[2] = record.getPartnerSubGroupName();
				cellValuesArray[3] = record.getContactName();				
				cellValuesArray[4] = record.getTitle();				
				cellValuesArray[5] = record.getBusinessCountry();
				cellValuesArray[6] = record.getBusinessPhone();
				cellValuesArray[7] = record.getBusinessExt();
				cellValuesArray[8] = record.getMobileCountry();
				cellValuesArray[9] = record.getMobilePhone();
				cellValuesArray[10] = record.getMobileExt();
				cellValuesArray[11] = record.getPersonalCountry();
				cellValuesArray[12] = record.getPersonalPhone();
				cellValuesArray[13] = record.getPersonalExt();
				cellValuesArray[14] = record.getPrimaryEmail();
				cellValuesArray[15] = record.getSecondaryEmail();
				cellValuesArray[16] = record.getTransactionType();
				cellValues.add(cellValuesArray);
			}
			addRow(sheet, cellValues);
		}
	}

}
