package com.fh.util;

import com.fh.annotation.ExcelAnnotation;
import com.fh.bean.vo.ExcelInfo;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ExportExcel {
    public static String exportExcel(List<?> list, Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException {
        ExcelInfo excelInfo = new ExcelInfo();
        //使用java的反射机制获取注解
        ExcelAnnotation excelAnnotation = (ExcelAnnotation)clazz.getAnnotation(ExcelAnnotation.class);
        if (excelAnnotation!=null){
            excelInfo.setTitle(excelAnnotation.title());
            excelInfo.setSheetName(excelAnnotation.sheetName());
            excelInfo.setMkdir(excelAnnotation.mkdir());
            List<String> columns=new ArrayList<String>();
            List<String> columnName=new ArrayList<String>();
            Field[] declaredFields = clazz.getDeclaredFields();
            for(int i = 0; i < declaredFields.length; i++){
                Field field = declaredFields[i];
                ExcelAnnotation annotation = field.getAnnotation(ExcelAnnotation.class);
                if(annotation!=null){
                    columns.add(field.getName());
                    columnName.add(annotation.column());
                }
            }
            excelInfo.setColumns(columns);
            excelInfo.setColumnName(columnName);
        }
        return Excel.excel(list, excelInfo);
    }
}
