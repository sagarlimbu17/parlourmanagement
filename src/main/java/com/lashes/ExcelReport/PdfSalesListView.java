package com.lashes.ExcelReport;

import com.lashes.entities.Sales;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class PdfSalesListView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Content-Disposition","attachment: filename=\"salesreport.pdf\"");
        List<Sales> salesList = (List<Sales>) model.get("salesList");

        Table table = new Table(9);
        table.addCell("salesId");
        table.addCell("category");
        table.addCell("name");
        table.addCell("vendor");
        table.addCell("quantity");
        table.addCell("totalPrice");
        table.addCell("salesType");
        table.addCell("createdDate");
        table.addCell("updateDate");

        for(Sales sales : salesList){
            table.addCell(String.valueOf(sales.getSalesId()));
            table.addCell(String.valueOf(sales.getCategory()));
            table.addCell(String.valueOf(sales.getName()));
            table.addCell(String.valueOf(sales.getVendor()));
            table.addCell(String.valueOf(sales.getQuantity()));
            table.addCell(String.valueOf(sales.getTotalPrice()));
            table.addCell(String.valueOf(sales.getSalesType()));
            table.addCell(String.valueOf(sales.getCreatedDate()));
        }
    }
}
