package com.example.hotelmanagementbackgroud.service;

import com.example.hotelmanagementbackgroud.model.Data;
import com.example.hotelmanagementbackgroud.model.homestate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Stateservice {
    public int changeState(String homecode);

    List<homestate> gettable();


}
