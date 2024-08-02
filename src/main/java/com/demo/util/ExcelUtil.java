package com.demo.util;

import com.demo.excel.CustomExcelExportData;
import com.demo.excel.ExcelExportData;
import com.demo.excel.annotation.ExcelColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtil {

    /**
     * 使用客製化物件產出Excel
     *
     * @param fileName            檔案名稱
     * @param excelExportDataList excel資料
     * @throws IOException
     */
    public static void export2(String fileName, List<CustomExcelExportData> excelExportDataList) throws IOException {

        HttpServletResponse response = getExcelExportResponse();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx;" + "filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();

        Workbook workbook = new XSSFWorkbook();
        processCustomExcelExportData(workbook, excelExportDataList);
        workbook.write(outputStream);
    }

    private static void processCustomExcelExportData(Workbook workbook, List<CustomExcelExportData> excelExportDataList) {
        if (CollectionUtils.isEmpty(excelExportDataList)) {
            return;
        }

        for (CustomExcelExportData excelExportData : excelExportDataList) {
            Sheet sheet = workbook.createSheet(excelExportData.getSheetName());
            processCustomRow(sheet, excelExportData.getCustomRowList());
        }
    }

    private static void processCustomRow(Sheet sheet, List<CustomExcelExportData.CustomRow> customRowList) {
        if (CollectionUtils.isEmpty(customRowList)) {
            return;
        }

        for (CustomExcelExportData.CustomRow customRow : customRowList) {
            int headerRowIdx = customRow.getStartRowIdx();
            processCustomHeader(sheet, headerRowIdx, customRow.getHeaderList());
            int dataRowIdx = headerRowIdx + customRow.getHeaderList().size();
            processCustomData(sheet, dataRowIdx, customRow.getDataList());
        }
    }

    private static void processCustomHeader(Sheet sheet, int rowIdx, List<List<CustomExcelExportData.CustomRow.CustomCell>> headerList) {
        if (CollectionUtils.isEmpty(headerList)) {
            return;
        }

        for (List<CustomExcelExportData.CustomRow.CustomCell> customCells : headerList) {
            Row headerRow = sheet.createRow(rowIdx++);
            processCustomCell(sheet, headerRow, customCells);
        }
    }

    private static void processCustomCell(Sheet sheet, Row row, List<CustomExcelExportData.CustomRow.CustomCell> customCells) {
        if (CollectionUtils.isEmpty(customCells)) {
            return;
        }

        int rowIdx = 0;
        for (CustomExcelExportData.CustomRow.CustomCell customCell : customCells) {
            if (customCell.isMerge()) {
                rowIdx = customCell.getFirstCol();
            }
            setCustomCell(sheet, row, rowIdx, customCell);
            rowIdx++;
        }
    }

    private static void processCustomData(Sheet sheet, int startRowIdx, List<?> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        for (Object obj : dataList) {
            Row dataRow = sheet.createRow(startRowIdx++);
            if (obj instanceof List objList) {
                processIfList(sheet, dataRow, objList);
            } else if (obj instanceof String str) {
                setExcelCell(dataRow, 0, str);
            } else {
                processIfObject(dataRow, obj);
            }
        }
    }

    private static void processIfObject(Row dataRow, Object obj) {
        if (obj == null) {
            return;
        }

        String[] values = getFieldValues(obj);
        for (int i = 0; i < values.length; i++) {
            setExcelCell(dataRow, i, values[i]);
        }
    }

    private static void processIfList(Sheet sheet, Row dataRow, List objList) {
        if (CollectionUtils.isEmpty(objList)) {
            return;
        }

        int rowIdx = 0;
        for (Object obj : objList) {
            if (obj instanceof CustomExcelExportData.CustomRow.CustomCell customCell) {
                if (customCell.isMerge()) {
                    rowIdx = customCell.getFirstCol();
                }
                setCustomCell(sheet, dataRow, rowIdx, customCell);
            } else if (obj instanceof String str) {
                setExcelCell(dataRow, rowIdx, str);
            }
            rowIdx++;
        }
    }

    private static void setCustomCell(Sheet sheet, Row row, int colIdx, CustomExcelExportData.CustomRow.CustomCell customCell) {
        if (customCell.isMerge()) {
            CellRangeAddress mergeCell = new CellRangeAddress(customCell.getFirstRow(), customCell.getLastRow(), customCell.getFirstCol(), customCell.getLastCol());
            sheet.addMergedRegion(mergeCell);
        }
        setExcelCell(row, colIdx, customCell.getName());
    }

    private static void setExcelCell(Row row, int colIdx, String name) {
        Cell cell = row.createCell(colIdx);
        cell.setCellValue(name);
    }

    public static CustomExcelExportData.CustomRow.CustomCell createCustomCell(String name) {
        return CustomExcelExportData.CustomRow.CustomCell.builder()
                .name(name)
                .isMerge(false)
                .build();
    }

    public static CustomExcelExportData.CustomRow.CustomCell createCustomCell(String name, int firstRow, int lastRow, int firstCol, int lastCol) {
        return CustomExcelExportData.CustomRow.CustomCell.builder()
                .name(name)
                .isMerge(true)
                .firstRow(firstRow)
                .lastRow(lastRow)
                .firstCol(firstCol)
                .lastCol(lastCol)
                .build();
    }


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
