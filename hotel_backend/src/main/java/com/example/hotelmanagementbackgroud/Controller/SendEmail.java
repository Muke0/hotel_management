package com.example.hotelmanagementbackgroud.Controller;

import com.example.hotelmanagementbackgroud.service.impl.EmailEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SendEmail {
    @Resource
    private EmailEntity emailEntity;

    @RequestMapping(value = "/getcode",method = RequestMethod.POST)
    public String Getcode(@RequestParam String email){
        int code = (int)(Math.random()*10000);
        while(code<1000){
            code = (int)(Math.random()*10000);
        }
        System.out.println(email);
        emailEntity.sendemail(email, "注册验证码", String.valueOf(code));
        return String.valueOf(code);
    }
}
