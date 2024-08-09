package com.ziyao.demo.util;

import com.ziyao.demo.excel.ExcelExportData;
import com.ziyao.demo.excel.annotation.ExcelColumn;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtil {

    public static void export(String fileName, List<ExcelExportData> excelExportDataList) throws IOException {

        HttpServletResponse response = getExcelExportResponse();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx;" + "filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();


        Workbook workbook = new XSSFWorkbook();

        if (CollectionUtils.isNotEmpty(excelExportDataList)) {
            for (ExcelExportData excelExportData : excelExportDataList) {
                Sheet sheet = workbook.createSheet(excelExportData.getSheetName());

                int rowIdx = 0;
                Row headerRow = sheet.createRow(rowIdx++);
                if (excelExportData.getClazz() != null) {
                    String[] headers = getFieldHeaders(excelExportData.getClazz());
                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(getHeaderStyle(workbook));
                    }
                }

                if (CollectionUtils.isNotEmpty(excelExportData.getDataList())) {
                    for (Object obj : excelExportData.getDataList()) {
                        Row dataRow = sheet.createRow(rowIdx++);

                        String[] values = getFieldValues(obj);
                        for (int i = 0; i < values.length; i++) {
                            Cell cell = dataRow.createCell(i);
                            cell.setCellValue(values[i]);
                        }
                    }
                }
            }
        }
        workbook.write(outputStream);
    }

    /**
     * 遍歷obj -> String[]
     *
     * @param obj
     * @return
     */
    private static String[] getFieldValues(Object obj) {
        List<String> result = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                result.add(String.valueOf(field.get(obj)));
            }
        } catch (Exception e) {
            log.error("ExcelUtil getFieldValues error : " + e);
        }
        return result.toArray(new String[0]);
    }

    /**
     * 遍歷class 取得annotation設定excel欄位名稱，沒有則取屬性名
     *
     * @param clazz
     * @return
     */
    private static String[] getFieldHeaders(Class<?> clazz) {
        List<String> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
                if (excelColumn != null) {
                    result.add(excelColumn.name());
                } else {
                    result.add(field.getName());
                }
            }
        } catch (Exception e) {
            log.error("ExcelUtil getFieldHeaders error : " + e);
        }
        return result.toArray(new String[0]);
    }

    /**
     * 自定義表頭顏色
     *
     * @param workbook
     * @return
     */
    private static CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerStyle;
    }

    /**
     * HttpServletResponse for excel export
     *
     * @return
     */
    public static HttpServletResponse getExcelExportResponse() {
        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        return response;
    }

}
