package com.example.five_two;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by 璐 on 2017/11/15.
 */

public class MyUser extends BmobUser {

    private Integer age;
    private Integer playscore;
    private String sex;
    private String nickname;
    private String confirmpassword;
    private String area;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPlayscore() {
        return playscore;
    }

    public void setPlayscore(Integer playscore) {
        this.playscore = playscore;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }









}
