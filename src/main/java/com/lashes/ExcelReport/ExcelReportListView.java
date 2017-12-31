package com.lashes.ExcelReport;

import com.lashes.entities.Sales;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelReportListView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition","attachment; filename=\"report.xls\"");

        List<Sales> salesList = (List<Sales>) model.get("salesList");
        Sheet sheet = workbook.createSheet("Sales Information");

        //header row

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("salesId");
        header.createCell(1).setCellValue("category");
        header.createCell(2).setCellValue("name");
        header.createCell(3).setCellValue("vendor");
        header.createCell(4).setCellValue("quantity");
        header.createCell(5).setCellValue("totalPrice");
        header.createCell(6).setCellValue("salesType");
        header.createCell(7).setCellValue("createdDate");
        int rowNum =1;

        for(Sales sales : salesList){
            Row row= sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sales.getSalesId());
            row.createCell(1).setCellValue(sales.getCategory());
            row.createCell(2).setCellValue(sales.getName());
            row.createCell(3).setCellValue(sales.getVendor());
            row.createCell(4).setCellValue(sales.getQuantity());
            row.createCell(5).setCellValue(sales.getTotalPrice());
            row.createCell(6).setCellValue(sales.getSalesType());
            row.createCell(7).setCellValue(sales.getCreatedDate());
            if(sales.getCategory()==null){
                row.getCell(1).setCellValue("none");
            }
            if(sales.getVendor()==null){
                row.getCell(3).setCellValue("none");
            }

        }


    }
}
