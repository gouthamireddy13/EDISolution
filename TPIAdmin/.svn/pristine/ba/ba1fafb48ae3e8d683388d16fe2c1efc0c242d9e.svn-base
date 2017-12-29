package com.abc.tpi.view.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.abc.tpi.model.reporting.PartnerSubscriptionRecord;

public class ExcelPartnerSubscriptionReport extends TpiXlsReport {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// change the file name
		String fileName = getFileName(model);
	
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName );

		String tabName = "IN Envelope";

		boolean isDirectionOut = false;
		
		@SuppressWarnings("unchecked")
		List<PartnerSubscriptionRecord> records = (List<PartnerSubscriptionRecord>) model.get("partnerSubscription");

		try {
			tabName = (model.get("srId")==null)?"IN Envelope":"IN Envelope for " + (String) model.get("srId");
		} catch (Exception ex) {
			tabName = "IN Envelope";
		}

		// excel tab name cannot exceed 30 characters
		if (tabName.length() >= 30) {
			tabName = tabName.substring(0, 29);
		}

		// create excel XLS sheet
		Sheet sheet = workbook.createSheet(tabName);

		
		if (model.get("direction")!=null && ((String)model.get("direction")).equalsIgnoreCase("out"))
		{
			String[] rowHeaders = { "TPName", "TransactionID", "Functionalcode", "DirectionI/O",
					"CustomerORSupplier (C or S)", "BusinessUnit", "Test_TPQualifier", "Test_TPISAID", "Test_TPGSID",
					"Prd_TPQualifier", "Prd_TPISAID", "Prd_TPGSID", "TPSegmentTerminator", "TPElementDelimiter",
					"TPSubElementDelimiiter", "TranslationMapName", "Version", "ComplianceChecking", "Test_ABCQual",
					"Test_ABCISAID", "Test_ABCGSID", "Prd_ABCQual", "Prd_ABCISAID", "Prd_ABCGSID", "Comments",
					"AckOverdueHours", "AckOverdueMinutes", "Acknowledgement","ST_ControlNum","ISA_ControlNum", "ST_AcceptorLookUpAlias","GSId_Version"};
			
			isDirectionOut = true;
			addHeaderRow(sheet, rowHeaders);
		}
		else
		{
			String[] rowHeaders = { "TPName", "TransactionID", "Functionalcode", "DirectionI/O",
					"CustomerORSupplier (C or S)", "BusinessUnit", "Test_TPQualifier", "Test_TPISAID", "Test_TPGSID",
					"Prd_TPQualifier", "Prd_TPISAID", "Prd_TPGSID", "TPSegmentTerminator", "TPElementDelimiter",
					"TPSubElementDelimiiter", "TranslationMapName", "Version", "ComplianceChecking", "Test_ABCQual",
					"Test_ABCISAID", "Test_ABCGSID", "Prd_ABCQual", "Prd_ABCISAID", "Prd_ABCGSID", "Comments",
					"AckOverdueHours", "AckOverdueMinutes", "Acknowledgement" };
					
			addHeaderRow(sheet, rowHeaders);
		}



		if (records != null && !records.isEmpty()) {

			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for (PartnerSubscriptionRecord record : records) {
				if (isDirectionOut)
				{
					cellValuesArray = new String[32];
				}
				else
				{
					cellValuesArray = new String[28];	
				}
					
				
				cellValuesArray[0] = record.getPartnerName();
				cellValuesArray[1] = String.valueOf(record.getTransactionId());
				cellValuesArray[2] = "";
				cellValuesArray[3] = record.getDirectionCode();				
				cellValuesArray[4] = record.getCustomerSupplierIndicator();				
				cellValuesArray[5] = record.getCompanyName().name();
				cellValuesArray[6] = record.getServiceTestQual();
				cellValuesArray[7] = record.getServiceTestSaId();
				cellValuesArray[8] = record.getServiceTestGsId();
				cellValuesArray[9] = record.getServiceProdQualifier();
				cellValuesArray[10] = record.getServiceProdSaId();
				cellValuesArray[11] = record.getServiceProdGsId();
				cellValuesArray[12] = record.getServiceSegementDelim();
				cellValuesArray[13] = record.getServiceElementDelim();
				cellValuesArray[14] = record.getServiceCompositeDelim();
				cellValuesArray[15] = record.getTranslationMapName();
				cellValuesArray[16] = String.valueOf(record.getBusinessServiceVersion());
				cellValuesArray[17] = ((record.getComplianceChecking()) ? "Yes" : "No");
				cellValuesArray[18] = record.getBusinessServiceTestQual();
				cellValuesArray[19] = record.getBusinessServiceTestSaId();
				cellValuesArray[20] = record.getBusinessServiceTestGsId();
				cellValuesArray[21] = record.getBusinessServiceProdQualifier();
				cellValuesArray[22] = record.getBusinessServiceProdSaId();
				cellValuesArray[23] = record.getBusinessServiceProdGsId();
				cellValuesArray[24] = "";
				cellValuesArray[25] = String.valueOf(record.getAckOverdueHours());
				cellValuesArray[26] = "0";
				cellValuesArray[27] = String.valueOf(record.isAck());
				if (isDirectionOut)
				{
					cellValuesArray[28] = record.getStControlNum();
					cellValuesArray[29] = record.getIsaControlNum();
					cellValuesArray[30] = record.getStAcceptorLookUpAlias();
					cellValuesArray[31] = record.getGsIdVersion();
				}
				cellValues.add(cellValuesArray);
			}

			addRow(sheet, cellValues);

		}

	}

	private String getFileName(Map<String, Object> model)
	{
		String fileName = "";
		
		try {
			if ( model.get("direction")!=null && model.get("srId")!=null)
			{
				fileName = (String) model.get("direction") + "_" + (String) model.get("srId") + ".xls";	
			}
			else
			{
				fileName = "report.xls";
			}
			
		} catch (Exception ex) {
			fileName = "report.xls";
		}
		return fileName;
	}
}
