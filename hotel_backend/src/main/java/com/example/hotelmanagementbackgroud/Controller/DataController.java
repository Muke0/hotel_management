package com.example.hotelmanagementbackgroud.Controller;


import com.example.hotelmanagementbackgroud.character.*;
import com.example.hotelmanagementbackgroud.model.Data;
import com.example.hotelmanagementbackgroud.service.impl.DataEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DataController {

    @Resource
    private DataEntity dataEntity;

    @RequestMapping(value = "/moredata", method = RequestMethod.POST)
    public Data Moredata(@RequestBody Data data){
        return dataEntity.getmoredata(data);
    }

    @RequestMapping(value = "/updatedata", method = RequestMethod.PUT)
    public String updateData(@RequestBody Data data){
        return dataEntity.updatedata(data);
    }


    @RequestMapping(value = "/getdata/{page}", method = RequestMethod.GET)
    public getdatashow getdata(@PathVariable int page){
        getdatashow x = new getdatashow();
        x.dataList = dataEntity.getdata(page);
        x.a = dataEntity.getallcount();
        return x;
    }

    @RequestMapping(value = "/searchdata/{identity}", method = RequestMethod.GET)
    public getdatashow searchdata(@PathVariable String identity){
        return dataEntity.searchdata(identity);
    }

    @RequestMapping(value = "/deletdata", method = RequestMethod.POST)
    public String deletdata(@RequestBody Data data){
        System.out.println(data.getName());
        dataEntity.deletdata(data);
        return "true";
    }

    @RequestMapping(value = "/adddata", method = RequestMethod.POST)
    public String adddata(@RequestBody Data data){
        dataEntity.adddata(data);
        return "true";
    }

    @RequestMapping(value = "/showdata/{number}")
    public databoth showdata(@PathVariable int number){
        return dataEntity.datashow(number);
    }

    @RequestMapping(value = "/showdata/today")
    public List<todayshowcharacter> todayshow(){
        return dataEntity.todayshow();
    }

    @RequestMapping(value = "/showincome")
    public income todayincome(){
        return dataEntity.todayincome();
    }

    @RequestMapping(value = "/beforehomeshow")
    public beforehomeshowcharacter BeforeHomeShow(){
        return dataEntity.beforehomeshow();
    }
}
