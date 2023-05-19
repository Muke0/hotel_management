package com.example.hotelmanagementbackgroud.Controller;

import com.example.hotelmanagementbackgroud.model.Data;
import com.example.hotelmanagementbackgroud.model.homestate;
import com.example.hotelmanagementbackgroud.service.impl.StateEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class HomestateController {

    @Resource
    StateEntity stateEntity;

    @RequestMapping(value = "/clean/{homecode}")
    public String suredel(@PathVariable String homecode){
        return String.valueOf(stateEntity.changeState(homecode));
    }

    //进入客房管理界面:路由: /room_table, GET
    //返回值为房间号+状态的list
    @RequestMapping(value = "/room_table", method = RequestMethod.GET)
    public List<homestate> gettable(){
        return stateEntity.gettable();
    }


}
