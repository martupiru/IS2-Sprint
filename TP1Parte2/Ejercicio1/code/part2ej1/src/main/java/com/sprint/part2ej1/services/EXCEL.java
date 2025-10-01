package com.sprint.part2ej1.services;

import com.sprint.part2ej1.entities.Empresa;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class EXCEL {

    @Autowired
    private EmpresaService empresaService;

    public void generateExcel(OutputStream outputStream) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empresas");


        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        Row header = sheet.createRow(0);

        Cell headerCell0 = header.createCell(0);
        headerCell0.setCellValue("ID");
        headerCell0.setCellStyle(headerStyle);

        Cell headerCell1 = header.createCell(1);
        headerCell1.setCellValue("Razón Social");
        headerCell1.setCellStyle(headerStyle);

        Cell headerCell2 = header.createCell(2);
        headerCell2.setCellValue("Eliminado");
        headerCell2.setCellStyle(headerStyle);


        List<Empresa> empresas = empresaService.listarEmpresasActivas();


        int rowCount = 1;
        for (Empresa empresa : empresas) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(empresa.getId());
            row.createCell(1).setCellValue(empresa.getRazonSocial());
            row.createCell(2).setCellValue(empresa.getEliminado() ? "Sí" : "No");
        }


        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }


        workbook.write(outputStream);
        workbook.close();
    }
}