package com.fh.util;

import com.fh.commons.ExcelAnnotation;
import com.fh.commons.ExcleHeard;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExceUtils {

    public static void excelUtil(List list, HttpServletResponse response){
        Object o = list.get(0);
        //获取类对象   对象.getClass
        Class aClass = o.getClass();
        //处理标题
        //获取title标题名  在类的注解上
        ExcleHeard annotation = (ExcleHeard) aClass.getAnnotation(ExcleHeard.class);
        //获取到标题名
        String title = annotation.title();
        XSSFWorkbook book=new XSSFWorkbook();
        //将标题名放进sheet页中
        XSSFSheet sheet = book.createSheet(title);
        XSSFRow row = sheet.createRow(0);

        //处理列名
        //获取类的所有属性
        Field[] declaredFields = aClass.getDeclaredFields();
        int cellNum=0;
        for (int i = 0; i <declaredFields.length ; i++) {
                //每一个具体属性
               Field fields=declaredFields[i];
                //判断此属性 是否为要导出的属性
               ExcelAnnotation annotation1 = fields.getAnnotation(ExcelAnnotation.class);
               if(annotation1!=null){
                   String name = annotation1.name();
                   XSSFCell cell = row.createCell(cellNum);
                   cell.setCellValue(name);
                   cellNum++;
               }
        }
        //处理数据信息
        for (int i = 0; i <list.size() ; i++) {
            Object o1 = list.get(i);
            XSSFRow row1 = sheet.createRow(i + 1);
            int celln=0;
            for (int j = 0; j <declaredFields.length ; j++) {
                    Field fields=declaredFields[j];
                boolean annotationPresent = fields.isAnnotationPresent(ExcelAnnotation.class);
                    if(annotationPresent==true){
                        XSSFCell cell = row1.createCell(celln);
                        try {
                            fields.setAccessible(true);
                            Object o2 = fields.get(o1);
                            if(o2!=null){
                                Class type = fields.getType();
                                if(type== Date.class){
                                    SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
                                    Date date= (Date) o2;
                                    String format = sim.format(date);
                                    cell.setCellValue(format);
                                }else {
                                    cell.setCellValue(o2.toString());
                                }
                            }else{
                                cell.setCellValue("");
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        celln++;
                    }
            }
}
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\""+ UUID.randomUUID().toString()+".xlsx\"");

            ServletOutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                book.write(outputStream);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

}
