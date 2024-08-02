package com.demo.excel.dto;

import com.demo.excel.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdmUserExcel {

    @ExcelColumn(name = "ID")
    private String id;

    @ExcelColumn(name = "USER_ID")
    private String userId;

    @ExcelColumn(name = "USER_NAME")
    private String userName;

    @ExcelColumn(name = "CREATE_TIME")
    private String createTime;

    @ExcelColumn(name = "CREATE_BY")
    private String createBy;
}
