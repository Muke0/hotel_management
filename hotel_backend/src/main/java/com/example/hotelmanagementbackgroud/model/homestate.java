package com.example.hotelmanagementbackgroud.model;

public class homestate {
    private Integer id;

    private String homecode;

    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHomecode() {
        return homecode;
    }

    public void setHomecode(String homecode) {
        this.homecode = homecode == null ? null : homecode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}