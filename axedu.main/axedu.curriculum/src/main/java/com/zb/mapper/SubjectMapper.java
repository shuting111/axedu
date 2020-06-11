package com.zb.mapper;

import com.zb.pojo.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubjectMapper {

	public Subject getSubjectById(@Param(value = "id") Long id)throws Exception;

	public List<Subject> getSubjectListByMap(Map<String, Object> param)throws Exception;

	public Integer getSubjectCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertSubject(Subject subject)throws Exception;

	public Integer updateSubject(Subject subject)throws Exception;



}
