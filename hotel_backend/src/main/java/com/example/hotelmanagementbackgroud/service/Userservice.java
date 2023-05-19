package com.example.hotelmanagementbackgroud.service;

import com.example.hotelmanagementbackgroud.model.User;

import java.util.List;

public interface Userservice {

    public int addUser(User user);
    public int forgetUser(String password, String email);//度
    public int identityUser(String username, String password);//度
    public List<User> supperControl();
    public int updateUsertrue(User user);
    public int updateUserfalse(User user);
}
