package com.example.hotelmanagementbackgroud.model;

public class Data {
    private Integer id;

    private String name;

    private String sex;

    private String homecode;

    private Long intime;

    private String identity;

    private String yuan;

    private Long outtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getHomecode() {
        return homecode;
    }

    public void setHomecode(String homecode) {
        this.homecode = homecode == null ? null : homecode.trim();
    }

    public Long getIntime() {
        return intime;
    }

    public void setIntime(Long intime) {
        this.intime = intime;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getYuan() {
        return yuan;
    }

    public void setYuan(String yuan) {
        this.yuan = yuan == null ? null : yuan.trim();
    }

    public Long getOuttime() {
        return outtime;
    }

    public void setOuttime(Long outtime) {
        this.outtime = outtime;
    }
}