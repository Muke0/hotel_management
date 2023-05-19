package com.example.hotelmanagementbackgroud.dao;

import com.example.hotelmanagementbackgroud.model.homestate;
import com.example.hotelmanagementbackgroud.model.homestateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface homestateMapper {
    long countByExample(homestateExample example);

    int deleteByExample(homestateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(homestate record);

    int insertSelective(homestate record);

    List<homestate> selectByExample(homestateExample example);

    homestate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") homestate record, @Param("example") homestateExample example);

    int updateByExample(@Param("record") homestate record, @Param("example") homestateExample example);

    int updateByPrimaryKeySelective(homestate record);

    int updateByPrimaryKey(homestate record);
}