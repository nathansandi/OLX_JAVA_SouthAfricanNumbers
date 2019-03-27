package com.olx.views;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.olx.entity.CorrectTelephones;
import com.olx.entity.IncorrectTelephones;



public class ExcelView extends AbstractXlsView{

		@Override
		protected void buildExcelDocument(Map<String, Object> model,
		                                  Workbook workbook,
		                                  HttpServletRequest request,
		                                  HttpServletResponse response) throws Exception {

			System.out.println("View Started...");
		    // change the file name
		    response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

		    System.out.println("Going Create the object...");
		    @SuppressWarnings("unchecked")
		    List<CorrectTelephones> cTeleph = (List<CorrectTelephones>) model.get("correcttelephones");
		 
		    @SuppressWarnings("unchecked")
		    List<IncorrectTelephones> IncTeleph = (List<IncorrectTelephones>) model.get("incorrecttelephones");
		    System.out.println("Object Created...");
		    System.out.println("INC TEST - "+IncTeleph.get(0));
		    
		    // create excel xls sheet
		    Sheet sheet = workbook.createSheet("Telephones Details");
		    sheet.setDefaultColumnWidth(30);
		    
		    Sheet sheetInc = workbook.createSheet("Rejected Telephones Details");
		    sheetInc.setDefaultColumnWidth(30);

		    // create style for header cells
		    CellStyle style = workbook.createCellStyle();


		    // create header row
		    Row header = sheet.createRow(0);
		    header.createCell(0).setCellValue("ID");
		    header.getCell(0).setCellStyle(style);
		    header.createCell(1).setCellValue("Telephone");
		    header.getCell(1).setCellStyle(style);
		    header.createCell(2).setCellValue("Fix");
		    header.getCell(2).setCellStyle(style);
		    
		    Row headerInc = sheetInc.createRow(0);
		    headerInc.createCell(0).setCellValue("ID");
		    headerInc.getCell(0).setCellStyle(style);
		    headerInc.createCell(1).setCellValue("Telephone");
		    headerInc.getCell(1).setCellStyle(style);
		   



		    int rowCount = 1;

		    for(CorrectTelephones tempTel : cTeleph){
		        Row userRow =  sheet.createRow(rowCount++);
		        userRow.createCell(0).setCellValue(tempTel.getId());
		        userRow.createCell(1).setCellValue(tempTel.getTelephone());
		        userRow.createCell(2).setCellValue(tempTel.getIsfixed());
		     }
		    

		    rowCount = 1;

		    for(IncorrectTelephones tempTelIn : IncTeleph){
		        Row userRow =  sheetInc.createRow(rowCount++);
		        userRow.createCell(0).setCellValue(tempTelIn.getId());
		        userRow.createCell(1).setCellValue(tempTelIn.getTelephone());
		     }

		}

		
}
