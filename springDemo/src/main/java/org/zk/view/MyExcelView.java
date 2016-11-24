package org.zk.view;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.zk.beans.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 10/30/2016.
 */
public class MyExcelView extends AbstractExcelView {

    protected void buildExcelDocument(Map<String, Object> model, org.apache.poi.hssf.usermodel.HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // get data model which is passed by the Spring container
        List<User> list = (List<User>) model.get("list");


        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet();
        //sheet.setDefaultColumnWidth((short)30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        // create header row
        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("id");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("username");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("age");
        header.getCell(2).setCellStyle(style);

        // create data rows
        int rowCount = 1;

        for (User aBook : list) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(aBook.getId());
            aRow.createCell(1).setCellValue(aBook.getUserName());
            aRow.createCell(2).setCellValue(aBook.getAge());
        }
    }
}
