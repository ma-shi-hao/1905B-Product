package com.fh.service.impl;

import com.fh.bean.po.StudentPo;
import com.fh.dao.StuDao;
import com.fh.service.StuService;
import com.fh.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StuServiceImpl implements StuService {
    @Resource
    private StuDao stuDao;

    @Override
    public void queryPageList(PageBean<StudentPo> page) {
         Long count=stuDao.queryCount();
         page.setRecordsFiltered(count);
         page.setRecordsTotal(count);
         List<StudentPo> list=stuDao.queryList(page);
         page.setList(list);
    }

    @Override
    public void addStu(StudentPo studentPo) {
        studentPo.setIsDel(1);
        stuDao.insert(studentPo);
    }

    @Override
    public void delStu(StudentPo studentPo) {
        studentPo.setIsDel(2);
         stuDao.updateById(studentPo);
    }

    @Override
    public StudentPo queryStuById(Integer id) {
        return stuDao.selectById(id);
    }

    @Override
    public void updateStu(StudentPo studentPo) {
        stuDao.updateById(studentPo);
    }
}
