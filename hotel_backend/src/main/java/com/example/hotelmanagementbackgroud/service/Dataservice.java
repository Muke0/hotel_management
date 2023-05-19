package com.example.hotelmanagementbackgroud.service;

import com.example.hotelmanagementbackgroud.character.*;
import com.example.hotelmanagementbackgroud.model.Data;

import java.util.List;

public interface Dataservice {
    public Data getmoredata(Data data);//度
    public String updatedata(Data data);//度
    public List<Data> getdata(int page);
    public getdatashow searchdata(String data);
    public int deletdata(Data data);
    public int adddata(Data data);
    public int getallcount();
    public databoth datashow(int i);
    public List<todayshowcharacter> todayshow();
    public income todayincome();//度
    public beforehomeshowcharacter beforehomeshow();
}
