package com.fh.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("msh_student")
public class StudentPo {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String stuName;
    private Integer isDel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer age;
    private String img;
    private String address;
    private String ips;
    //assasasassa
}
