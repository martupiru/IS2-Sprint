package com.gimnasio.gimnasio.services;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class ReportService {

    /**
     * Genera un PDF a partir de un .jasper que debe estar en src/main/resources/reports/
     *
     * @param reportName nombre del archivo .jasper
     * @param params     mapa de parámetros para Jasper
     * @param dataSource JRDataSource con los items
     * @return bytes del PDF
     */
    public byte[] generarReport(String reportName, Map<String, Object> params, JRDataSource dataSource) throws JRException {
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/" + reportName + ".jasper");
        if (reportStream == null) {
            throw new JRException("No se encontró el reporte: /reports/" + reportName + ".jasper");
        }

        if (params == null) params = java.util.Collections.emptyMap();
        if (dataSource == null) dataSource = new JREmptyDataSource();

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
