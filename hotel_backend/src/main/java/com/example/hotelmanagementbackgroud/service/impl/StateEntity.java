package com.example.hotelmanagementbackgroud.service.impl;

import com.example.hotelmanagementbackgroud.dao.DataMapper;
import com.example.hotelmanagementbackgroud.dao.homestateMapper;
import com.example.hotelmanagementbackgroud.model.Data;
import com.example.hotelmanagementbackgroud.model.DataExample;
import com.example.hotelmanagementbackgroud.model.homestate;
import com.example.hotelmanagementbackgroud.model.homestateExample;
import com.example.hotelmanagementbackgroud.service.Stateservice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StateEntity implements Stateservice {


    @Resource
    private homestateMapper homestatemapper;


    @Override
    @Transactional
    public int changeState(String homecode) {
        homestateExample homestateexample = new homestateExample();
        homestateExample.Criteria criteria = homestateexample.createCriteria();
        criteria.andHomecodeEqualTo(homecode);
        List<homestate> my_homestate = homestatemapper.selectByExample(homestateexample);
        my_homestate.get(0).setState("1");
        homestatemapper.updateByPrimaryKeySelective(my_homestate.get(0));
        return 1;
    }

    @Override
    @Transactional
    public List<homestate> gettable(){
        homestateExample homestateexample = new homestateExample();

        List<homestate> my_table = homestatemapper.selectByExample(homestateexample);
        if(my_table.size() == 0){
            return null;
        }else{
            return my_table;
        }
    }



}
