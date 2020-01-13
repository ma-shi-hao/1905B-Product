package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.bean.po.StudentPo;
import com.fh.util.PageBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StuDao extends BaseMapper<StudentPo> {
    Long queryCount();

    List<StudentPo> queryList(PageBean<StudentPo> page);

/*
    List<StudentPo> queryStuById(Integer id);
*/
}
