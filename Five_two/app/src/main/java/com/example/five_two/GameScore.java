package com.example.five_two;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by 璐 on 2017/11/30.
 */

public class GameScore extends BmobObject {

    private static final long serialVersionUID = 1L;

    public GameScore() {
        super();
    }
    public GameScore(String GameScore){
        super(GameScore);
    }

    /**
     * 玩家
     */
    private MyUser player;

    /**
     * 玩家昵称--对应User表的用户名
     */
    private String name;
    /**
     * 游戏得分
     */
    private Integer playscore;

    /**
     * 用户昵称
     */
    private  String nickname;

    /**
     * 游戏（玩家所玩的游戏）
     */
    private String gamefivestar;

    private BmobGeoPoint gps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobGeoPoint getGps() {
        return gps;
    }

    public void setGps(BmobGeoPoint gps) {
        this.gps = gps;
    }

    public MyUser getPlayer() {
        return player;
    }

    public void setPlayer(MyUser player) {
        this.player = player;
    }

    /*public String getGamefivestar() {
        return gamefivestar;
    }

    public void setGamefivestar(String game) {
        this.gamefivestar = gamefivestar;
    }*/

    public Integer getPlayscore() {
        return playscore;
    }

    public void setPlayscore(Integer playscore) {
        this.playscore = playscore;
    }

    //public Integer getSignScore() {
        //return signScore;
    //}

    //public void setSignScore(Integer signScore) {
       // this.signScore = signScore;
   // }



}
