package com.example.hotelmanagementbackgroud.Controller;

import com.example.hotelmanagementbackgroud.model.User;
import com.example.hotelmanagementbackgroud.service.impl.UserEntity;
import org.apache.el.parser.Token;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserEntity userEntity;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Userregister(@RequestBody User user) {

        int row = userEntity.addUser(user);
        if (row == -1) {

            //邮箱存在
            return "false1";
        } else if (row == -2) {
            //用户名存在
            return "false2";
        } else {
            return "true";
        }
    }

    @RequestMapping(value = "/forgetfind", method = RequestMethod.POST)
    public String Userforget(@RequestBody User user){
        int row = userEntity.forgetUser(user.getPassword(), user.getEmail());
        if (row != -1){
            return "true";
        }
        else
        {
            return "false";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String UserLogin(@RequestBody User user, HttpSession session){
        int a = userEntity.identityUser(user.getUsername(), user.getPassword());
        if(a == -1){
            return "false1";
        }else if(a == -2){
            return "false2";
        } else if (a == 1) {
            return "true1";
        } else if (a == 2) {
            return "true2";
        }
        return null;
    }

    @RequestMapping(value = "/supperControl")
    public List<User> supperControl(){
        return userEntity.supperControl();
    }

    @RequestMapping(value = "/updateUsertrue")
    public String updateUsertrue(@RequestBody User user){
        userEntity.updateUsertrue(user);
        return "true";
    }

    @RequestMapping(value = "/updateUserfalse")
    public String updateUserfalse(@RequestBody User user){
        userEntity.updateUserfalse(user);
        return "true";
    }
}
