package com.fh;

import com.fh.bean.User;
import com.fh.common.ExcelAnnotation;
import com.fh.common.ExcelTableAnnotation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class test1 {

    @Test
     public void getSql(){
        StringBuilder sb=new StringBuilder();
        Class<User> userClass = User.class;
        ExcelTableAnnotation annotation = userClass.getAnnotation(ExcelTableAnnotation.class);
        String table = annotation.table();

        Field[] declaredFields = userClass.getDeclaredFields();

        for (int i = 0; i <declaredFields.length ; i++) {
                    Field field=declaredFields[i];
            boolean annotationPresent = field.isAnnotationPresent(ExcelAnnotation.class);
            
        }

        sb.append("select * ").append("from ").append(table);
        System.out.println(sb);
    }
}
