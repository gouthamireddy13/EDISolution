package com.abc.tpi.view.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.abc.tpi.model.reporting.LightWellPartnerRecord;

public class LightWellIdentityReport extends TpiXlsxReport {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "LightWellIdentity.xlsx";
	
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName );

		String tabName = "Identity";

		@SuppressWarnings("unchecked")
		List<LightWellPartnerRecord> records = (List<LightWellPartnerRecord>) model.get("lightWellIdentity");

		// excel tab name cannot exceed 30 characters
		if (tabName.length() >= 30) {
			tabName = tabName.substring(0, 29);
		}

		// create excel XLS sheet
		Sheet sheet = workbook.createSheet(tabName);


		String[] rowHeaders = { "id", 
								"clientId", 
								"b2bIdentifier", 
								"orgName",
								"notes", 
								"identityExtensions", 
								"activeFlag"
							};
			
			addHeaderRow(sheet, rowHeaders);



		if (records != null && !records.isEmpty()) {

			ArrayList<String[]> cellValues = new ArrayList<String[]>();
			String[] cellValuesArray = null;

			for (LightWellPartnerRecord record : records) {
				
				cellValuesArray = new String[7];
				cellValuesArray[0] = "1";
				cellValuesArray[1] = "1";
				cellValuesArray[2] = record.getB2bIdentifier();
				cellValuesArray[3] = record.getOrgName();				
				cellValuesArray[4] = "";				
				cellValuesArray[5] = "";
				cellValuesArray[6] = "Y";
				cellValues.add(cellValuesArray);
			}
			addRow(sheet, cellValues);
		}
	}
}
