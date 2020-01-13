package com.fh.util;

import com.fh.bean.vo.ExcelInfo;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Excel {
    public static String excel(List<?> list, ExcelInfo excelInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, FileNotFoundException {
        //声明工作簿
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建sheet
        XSSFSheet sheet=workbook.createSheet();
        //创建row
        XSSFRow row=sheet.createRow(0);
        //创建单元格并给它赋值
        row.createCell(0).setCellValue(excelInfo.getTitle());
        //合并单元格
        CellRangeAddress rangeAddress=new CellRangeAddress(0, 0, 0, excelInfo.getColumns().size()-1);
        sheet.addMergedRegion(rangeAddress);

        PoiCellStyle.titleStyle(workbook);
        //创建字段单元格
        XSSFRow columnRow=sheet.createRow(1);
        List<String> columnName = excelInfo.getColumnName();
        for(int i = 0; i < columnName.size(); i++){
            String column = columnName.get(i);
            XSSFCell cell = columnRow.createCell(i);
            cell.setCellValue(column);
        }

        List<String> columns = excelInfo.getColumns();
        for(int i = 0; i < list.size(); i++){
            Object obj = list.get(i);
            //创建数据行
            XSSFRow listRow = sheet.createRow(i+2);
            //遍历数据集合
            for(int j = 0; j < columns.size(); j++){
                //创建单元格
                XSSFCell listCell=listRow.createCell(j);
                //获取一个get方法名
                String methodName=getMethodName(columns.get(j));
                //获取一个方法
                Method method = obj.getClass().getMethod(methodName);
                //执行方法
                Object invoke = method.invoke(obj);
                //判断返回返回值类型
                if(invoke instanceof String){
                    listCell.setCellValue((String) invoke);
                }else if(invoke instanceof Integer){
                    listCell.setCellValue((Integer) invoke);
                }else {
                    XSSFCellStyle style=workbook.createCellStyle();
                    XSSFDataFormat dataFormat= workbook.createDataFormat();
                    style.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
                    listCell.setCellValue((Date) invoke);
                    listCell.setCellStyle(style);
                }
            }
        }
        return uploadExcel(workbook, excelInfo);
    }

    //获取方法的名称
    public static  String getMethodName(String column){
        return "get"+column.substring(0,1).toUpperCase()+column.substring(1);
    }

    //创建文件目录
    public static String createMkdir(ExcelInfo excelInfo) throws FileNotFoundException {
        HttpServletRequest request=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        //获取项目的根目录
        File path=new File(ResourceUtils.getURL("classpath:").getPath());
        File realPath = new File(path.getAbsolutePath(),"start/excel"+excelInfo.getMkdir());
        if(!realPath.exists()){
            realPath.mkdirs();
        }
        return realPath.toString();
    }

    //生成新的文件名称
    public static String newFileName(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid+".xlsx";
    }

    //上传excel文件到指定文件夹
    public static String uploadExcel(XSSFWorkbook workbook,ExcelInfo excelInfo) throws FileNotFoundException {
        //创建文件目录
        String realPath=createMkdir(excelInfo);
        //获取新文件名
        String newFileName=newFileName();
        //输入流
        ByteArrayOutputStream bos=null;
        //输出流
        FileOutputStream fos = null;
        try {
            fos=new FileOutputStream(realPath+ "/" + newFileName);
            bos=new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] bytes=bos.toByteArray();
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return excelInfo.getMkdir()+ "/" + newFileName;
    }
}
