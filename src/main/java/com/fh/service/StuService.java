package com.fh.service;

import com.fh.bean.po.StudentPo;
import com.fh.util.PageBean;

public interface StuService {
    void queryPageList(PageBean<StudentPo> page);

    void addStu(StudentPo studentPo);

    void delStu(StudentPo studentPo);

    StudentPo queryStuById(Integer id);

    void updateStu(StudentPo studentPo);
}
