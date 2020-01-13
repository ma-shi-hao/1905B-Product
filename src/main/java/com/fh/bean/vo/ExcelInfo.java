package com.fh.bean.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExcelInfo {
    private String title;
    private String sheetName;
    private String mkdir;
    private List<String> columns;
    private List<String> columnName;
}
