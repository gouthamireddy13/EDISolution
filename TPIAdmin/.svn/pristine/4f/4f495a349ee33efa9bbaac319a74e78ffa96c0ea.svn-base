package com.abc.tpi.view.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.abc.tpi.model.reporting.TPPRecord;

public class ExcelTPPReport extends TpiXlsxReport {
	

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String fileName = "SampleTPPDataloadTemplate.xlsx";
	
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName );

		String tabName = "TPP";

		@SuppressWarnings("unchecked")
		List<TPPRecord> records = (List<TPPRecord>) model.get("tppRecord");

		// excel tab name cannot exceed 30 characters
		if (tabName.length() >= 30) {
			tabName = tabName.substring(0, 29);
		}

		// create excel XLS sheet
		Sheet sheet = workbook.createSheet(tabName);


		String[] rowHeaders = { "3PP NAME", 
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
								"Transaction Type",
							};
			
			addHeaderRow(sheet, rowHeaders);



		if (records != null && !records.isEmpty()) {

			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for (TPPRecord record : records) {
				
				cellValuesArray = new String[34];
				cellValuesArray[0] = record.getTppName();
				cellValuesArray[1] = record.getTppTypeCode();
				cellValuesArray[2] = record.getTestISAid();
				cellValuesArray[3] = record.getTsetISAQual();				
				cellValuesArray[4] = record.getTestGSid();				
				cellValuesArray[5] = record.getProdISAid();
				cellValuesArray[6] = record.getProdISAQual();
				cellValuesArray[7] = record.getProdGSid();
				cellValuesArray[8] = record.getProtocol1();
				cellValuesArray[9] = record.getProtocol2();
				cellValuesArray[10] = record.getProtocol3();
				cellValuesArray[11] = record.getProtocol4();
				cellValuesArray[12] = record.getProtocol5();
				cellValuesArray[13] = record.getTransactionDirection();
				cellValuesArray[14] = record.getTransactionType();
				cellValuesArray[15] = record.getTransactionVersion();
				cellValuesArray[16] = record.getSegmentDelimiter();
				cellValuesArray[17] = record.getElementDelimiter();
				cellValuesArray[18] = record.getCompositeDelimiter();
				cellValuesArray[19] = record.getRepeatDelimiter();
				cellValuesArray[20] = record.getContactName();
				cellValuesArray[21] = record.getTitle();
				cellValuesArray[22] = record.getBusinessCountry();
				cellValuesArray[23] = record.getBusinessPhone();
				cellValuesArray[24] = record.getBusinessExt();
				cellValuesArray[25] = record.getMobileCountry();
				cellValuesArray[26] = record.getMobilePhone();
				cellValuesArray[27] = record.getMobileExt();
				cellValuesArray[28] = record.getPersonalCountry();
				cellValuesArray[29] = record.getPersonalPhone();
				cellValuesArray[30] = record.getPersonalExt();
				cellValuesArray[31] = record.getPrimaryEmail();
				cellValuesArray[32] = record.getSecondaryEmail();
				cellValuesArray[33] = record.getTransactionType1();
				
				cellValues.add(cellValuesArray);
			}
			addRow(sheet, cellValues);
		}
	}


	

}
