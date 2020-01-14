package com.fh.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.commons.ExcelAnnotation;
import com.fh.commons.ExcleHeard;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("msh_student")
//@ExcelAnnotation(title = "学生信息", sheetName = "学生信息", mkdir = "student")
@ExcleHeard(title = "学生信息")
public class StudentPo {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //@ExcelAnnotation(column = "学生姓名")
    @ExcelAnnotation(name="学生姓名")
    private String stuName;
    private Integer isDel;
    //@ExcelAnnotation(column = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelAnnotation(name="生日")
    private Date birthday;
    @ExcelAnnotation(name="年龄")
    //@ExcelAnnotation(column = "年龄")
    private Integer age;
    @ExcelAnnotation(name="图片路径")
    //@ExcelAnnotation(column = "图片路径")
    private String img;
    @ExcelAnnotation(name="地区")
   // @ExcelAnnotation(column = "地区")
    private String address;
    private String ips;
}
