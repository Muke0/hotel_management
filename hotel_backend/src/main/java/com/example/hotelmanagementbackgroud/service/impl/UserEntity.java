package com.example.hotelmanagementbackgroud.service.impl;

import com.example.hotelmanagementbackgroud.dao.UserMapper;
import com.example.hotelmanagementbackgroud.model.User;
import com.example.hotelmanagementbackgroud.model.UserExample;
import com.example.hotelmanagementbackgroud.service.Userservice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserEntity implements Userservice {

    @Resource
    private UserMapper userMapper;


    @Override
    @Transactional
    public int addUser(User user) {
        UserExample userExample1 = new UserExample();
        UserExample userExample2 = new UserExample();
        UserExample.Criteria criteria = userExample1.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> my_user = userMapper.selectByExample(userExample1);
        if(my_user.size() == 0) {

            UserExample.Criteria criteria1 = userExample2.createCriteria();
            criteria1.andEmailEqualTo(user.getEmail());
            List<User> my_user1 = userMapper.selectByExample(userExample2);
            if(my_user1.size() == 0) {
                user.setIsuser(0);
                user.setIssupper(0);
                int row = userMapper.insert(user);
                return row;
            }
            else {
                return -1;
            }
        }
        else {
            return -2;
        }

    }

    @Transactional
    public int forgetUser(String password, String email){
        UserExample userExample1 = new UserExample();
        UserExample.Criteria criteria = userExample1.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> my_user = userMapper.selectByExample(userExample1);
        if(my_user.size() != 0){
            my_user.get(0).setPassword(password);
            int row = userMapper.updateByPrimaryKeySelective(my_user.get(0));
            return row;
        }
        else {
            return -1;
        }
    }

    @Transactional
    public int identityUser(String username, String password){
        UserExample userExample1 = new UserExample();
        UserExample.Criteria criteria = userExample1.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<User> my_user = userMapper.selectByExample(userExample1);
        if(my_user.size() != 0){
            if(my_user.get(0).getIsuser() == 1){
                if(my_user.get(0).getIssupper() == 1)
                {
                    //超级管理
                    return 2;
                }
                //已认证
                return 1;
            }
            //未认证
            return -2;
        }
        else {
            //不存在
            return -1;
        }
    }

    @Override
    @Transactional
    public List<User> supperControl() {
        UserExample userExample = new UserExample();
        List<User> my_user = userMapper.selectByExample(userExample);
        List<User> x_user = new ArrayList<>();
        for(User x:my_user){
            if(x.getIssupper() == 0){
                x_user.add(x);
            }
        }
        return x_user;
    }

    @Override
    @Transactional
    public int updateUsertrue(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        List<User> my_user = userMapper.selectByExample(userExample);
        my_user.get(0).setIsuser(1);
        userMapper.updateByPrimaryKeySelective(my_user.get(0));
        return 0;
    }

    @Override
    @Transactional
    public int updateUserfalse(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        List<User> my_user = userMapper.selectByExample(userExample);
        my_user.get(0).setIsuser(0);
        userMapper.updateByPrimaryKeySelective(my_user.get(0));
        return 0;
    }


}
