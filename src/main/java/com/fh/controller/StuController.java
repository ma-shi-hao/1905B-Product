package com.fh.controller;

import com.fh.bean.po.StudentPo;
import com.fh.service.StuService;
import com.fh.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/stu")
public class StuController {
    @Autowired
    private StuService stuService;

    @RequestMapping("/queryPageList")
        public PageBean<StudentPo> queryPageList(PageBean<StudentPo> page){
             stuService.queryPageList(page);
             return page;
    }
    @RequestMapping("/addStu")
    public StudentPo addStu(StudentPo studentPo){
         stuService.addStu(studentPo);
         return studentPo;
    }
    /**
     * 图片上传
     */
    @RequestMapping("/initFileInput")
    public Map initFileInput(@RequestParam("upload") MultipartFile upload, HttpServletRequest request) throws IOException {
        String path= request.getServletContext().getRealPath("/upload/");
        String uploadFileName= xiazai.upload(upload.getInputStream(),upload.getOriginalFilename(),path,request);
        Map map=new HashMap();
        String filePath = AliyunOssUtil.uploadOSSFree(upload, uploadFileName);
        map.put("realPath",filePath);
        return map;
    }

    @RequestMapping("/delStu")
    public void  delStu(StudentPo studentPo){
        stuService.delStu(studentPo);
    }
    @RequestMapping("/queryStuById")
    public Map queryStuById(Integer id){
        Map map=new HashMap();
        StudentPo studentPo=stuService.queryStuById(id);
        map.put("data",studentPo);
        return map;
    }
    @RequestMapping("/updateStu")
    public StudentPo updateStu(StudentPo studentPo){
        stuService.updateStu(studentPo);
        return studentPo;
    }
    @RequestMapping("importExcel")
    public String importExcel() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException {
        List<StudentPo> studentList = stuService.queryStudentList();
        if (studentList.size() == 0){
            return "500";
        }
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getIsDel() == 2){
                return "400";
            }
        }
        ExportExcel.exportExcel(studentList, StudentPo.class);
        return "200";
    }
    @RequestMapping("test")
    public void downexcel(HttpServletResponse response){
        List<StudentPo> list=stuService.querylistStudent();
        ExceUtils.excelUtil(list,response);
    }
}
