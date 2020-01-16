package com.fh.bean;

import com.fh.common.ExcelAnnotation;
import com.fh.common.ExcelTableAnnotation;
import lombok.Data;

@Data
@ExcelTableAnnotation(table = "user")
public class User {
    @ExcelAnnotation(column = "id")
    private Integer id;
   // @ExcelAnnotation(column = "stuName")
    private String stuName;
    //@ExcelAnnotation(column = "age")
    private Integer age;
}
