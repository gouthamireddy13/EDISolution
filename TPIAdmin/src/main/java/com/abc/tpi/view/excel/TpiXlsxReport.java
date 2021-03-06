package com.abc.tpi.view.excel;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public abstract class TpiXlsxReport extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> arg0, Workbook arg1, HttpServletRequest arg2,
			HttpServletResponse arg3) throws Exception {
		// TODO Auto-generated method stub

	}
	

	protected CellStyle getTpiGenericHeaderRowCellStyle(Workbook workbook)
	{
		CellStyle headerStyle = null;

		headerStyle = workbook.createCellStyle();        
		XSSFFont font = (XSSFFont) workbook.createFont();
       
		font.setFontHeightInPoints((short) 9);
		font.setFontName("Arial");
		font.setBold(true);

		//alignment
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
		//border
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		
		headerStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("General"));        
		headerStyle.setFillForegroundColor(new HSSFColor.LIGHT_TURQUOISE().getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setWrapText(false);
		headerStyle.setShrinkToFit(true);
		headerStyle.setFont(font);
		

		return headerStyle;
	}
	
	
	protected CellStyle getTpiGenericValueRowCellStyle(Workbook workbook)
	{
		return getTpiGenericValueRowCellStyle(workbook, "General");
	}
	
	protected CellStyle getTpiGenericValueRowCellStyle(Workbook workbook, String formatName)
	{
		CellStyle rowStyle = null;

		rowStyle = workbook.createCellStyle();        
		XSSFFont font = (XSSFFont) workbook.createFont();
       
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Verdana");
		font.setBold(false);

		//alignment
		rowStyle.setAlignment(HorizontalAlignment.CENTER);
        
		//border
		rowStyle.setBorderBottom(BorderStyle.THIN);
		rowStyle.setBorderLeft(BorderStyle.THIN);
		rowStyle.setBorderRight(BorderStyle.THIN);
		rowStyle.setBorderTop(BorderStyle.THIN);
		
		rowStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat(formatName));        
		rowStyle.setWrapText(true);
		rowStyle.setFont(font);
		

		return rowStyle;
	}
	
	protected void addHeaderRow(Sheet sheet, String[] headerColumns)
	{
			if (headerColumns != null && sheet != null)			
			{
				Row header = sheet.createRow(0);
				CellStyle rowHeaderStyle = getTpiGenericHeaderRowCellStyle(sheet.getWorkbook());
				
				for (int i=0; i<headerColumns.length; i++)
				{
					Cell cell = header.createCell(i);
			        cell.setCellValue(headerColumns[i]);			        
					cell.setCellStyle(rowHeaderStyle);
					sheet.autoSizeColumn(i);
				}
			}
	}
	
	protected void addRow(Sheet sheet, ArrayList<String[]> rowValues)
	{
		if (sheet != null && rowValues!=null)			
		{
			int i = 1;
			CellStyle rowStyle = null;			
			for (String[] values : rowValues)
			{
				if (values!=null)
				{					
					Row valueRow = sheet.createRow(i);
					
					for (int j=0; j<values.length; j++)
					{
						Cell cell = valueRow.createCell(j);
			        	if (org.apache.commons.lang3.StringUtils.isNumeric(values[j]))
			        	{
			        		rowStyle =  getTpiGenericValueRowCellStyle(sheet.getWorkbook(),"0");
			        		cell.setCellValue(Long.parseLong(values[j]));
			        	}
			        	else
			        	{
			        		rowStyle =  getTpiGenericValueRowCellStyle(sheet.getWorkbook());
			        		cell.setCellValue(values[j]);
			        	}			        		        
						cell.setCellStyle(rowStyle);
			        	
					}					
					i++;
				}
			}
		}
	}
	
}
