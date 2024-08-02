package com.demo.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomExcelExportData {

    private String sheetName;
    private List<CustomRow> customRowList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomRow {
        // 起始欄位
        private int startRowIdx;
        // 表頭
        private List<List<CustomCell>> headerList;
        private List<?> dataList;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class CustomCell {

            private String name;
            private boolean isMerge;
            private int firstRow;
            private int lastRow;
            private int firstCol;
            private int lastCol;
        }
    }
}
