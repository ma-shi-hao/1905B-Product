package com.fh.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.annotation.ExcelAnnotation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("msh_student")
@ExcelAnnotation(title = "学生信息", sheetName = "学生信息", mkdir = "student")
public class StudentPo {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @ExcelAnnotation(column = "学生姓名")
    private String stuName;
    private Integer isDel;
    @ExcelAnnotation(column = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @ExcelAnnotation(column = "年龄")
    private Integer age;
    @ExcelAnnotation(column = "图片路径")
    private String img;
    @ExcelAnnotation(column = "地区")
    private String address;
    private String ips;
}
