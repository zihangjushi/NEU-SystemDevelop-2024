package com.NEUSystemDevelop2024.controller.explorer;

import com.NEUSystemDevelop2024.entity.Course;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelExporter {
    public ByteArrayOutputStream exportToExcel(List<Course> courseList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Courses");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Course ID");
        headerRow.createCell(1).setCellValue("Course Name");
        headerRow.createCell(2).setCellValue("Company Name");
        headerRow.createCell(3).setCellValue("Description");
        headerRow.createCell(4).setCellValue("Course Order");
        headerRow.createCell(5).setCellValue("Author");
        headerRow.createCell(6).setCellValue("Create Time");
        headerRow.createCell(7).setCellValue("Modify Time");
        headerRow.createCell(8).setCellValue("Image URL (base64)");
        headerRow.createCell(9).setCellValue("Video URL (base64)");

        // Populate data rows
        int rowNum = 1;
        for (Course course : courseList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(course.getCourseId());
            row.createCell(1).setCellValue(course.getCourseName());
            row.createCell(2).setCellValue(course.getCompanyName());
            row.createCell(3).setCellValue(course.getDescription());
            row.createCell(4).setCellValue(course.getCourseOrder());
            row.createCell(5).setCellValue(course.getAuthor());
            row.createCell(6).setCellValue(course.getCreateTime().toString());
            row.createCell(7).setCellValue(course.getModifyTime().toString());
            row.createCell(8).setCellValue(course.getImageUrl());
            row.createCell(9).setCellValue(course.getVideoUrl());
        }

        // Resize columns to fit the content
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
