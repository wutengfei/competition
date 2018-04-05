package com.soundon.model;

import java.util.ArrayList;

/**
 * Created by Vince on 2017/11/25.
 */

public class TopListSet extends ArrayList<TopList> {
    private static TopListSet topListSet = null;

    private TopListSet(){}

    private void init(){
        TopList topList0 = new TopList();
        topList0.setIdx("0");
        topList0.setName("云音乐新歌榜");
        topList0.setPic("http://p1.music.126.net/N2HO5xfYEqyQ8q6oxCw8IQ==/18713687906568048.jpg");
        topListSet.add(topList0);
        TopList topList1 = new TopList();
        topList1.setIdx("1");
        topList1.setName("云音乐热歌榜");
        topList1.setPic("http://p1.music.126.net/GhhuF6Ep5Tq9IEvLsyCN7w==/18708190348409091.jpg");
        topListSet.add(topList1);
        TopList topList3 = new TopList();
        topList3.setIdx("3");
        topList3.setName("云音乐飙升榜");
        topList3.setPic("http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg");
        topListSet.add(topList3);
        TopList topList4 = new TopList();
        topList4.setIdx("4");
        topList4.setName("云音乐新电力榜");
        topList4.setPic("http://p1.music.126.net/qN-sDLqehN1oHGyov-0EZw==/109951163068669685.jpg");
        topListSet.add(topList4);
        TopList topList5 = new TopList();
        topList5.setIdx("5");
        topList5.setName("UK排行榜周榜");
        topList5.setPic("http://p1.music.126.net/VQOMRRix9_omZbg4t-pVpw==/18930291695438269.jpg");
        topListSet.add(topList5);
        TopList topList6 = new TopList();
        topList6.setIdx("6");
        topList6.setName("美国Billboard周榜");
        topList6.setPic("http://p1.music.126.net/EBRqPmY8k8qyVHyF8AyjdQ==/18641120139148117.jpg");
        topListSet.add(topList6);
        TopList topList8 = new TopList();
        topList8.setIdx("8");
        topList8.setName("iTunes榜");
        topList8.setPic("http://p1.music.126.net/83pU_bx5Cz0NlcTq-P3R3g==/18588343581028558.jpg");
        topListSet.add(topList8);
        TopList topList9 = new TopList();
        topList9.setIdx("9");
        topList9.setName("Hit FM Top榜");
        topList9.setPic("http://p1.music.126.net/54vZEZ-fCudWZm6GH7I55w==/19187577416338508.jpg");
        topListSet.add(topList9);
        TopList topList10 = new TopList();
        topList10.setIdx("10");
        topList10.setName("日本Oricon周榜");
        topList10.setPic("http://p1.music.126.net/Rgqbqsf4b3gNOzZKxOMxuw==/19029247741938160.jpg");
        topListSet.add(topList10);
        TopList topList14 = new TopList();
        topList14.setIdx("14");
        topList14.setName("中国TOP排行榜(港台榜)");
        topList14.setPic("http://p1.music.126.net/JPh-zekmt0sW2Z3TZMsGzA==/18967675090783713.jpg");
        topListSet.add(topList14);
        TopList topList15 = new TopList();
        topList15.setIdx("15");
        topList15.setName("中国TOP排行榜(内地榜)");
        topList15.setPic("http://p1.music.126.net/2klOtThpDQ0CMhOy5AOzSg==/18878614648932971.jpg");
        topListSet.add(topList15);
        TopList topList16 = new TopList();
        topList16.setIdx("16");
        topList16.setName("香港电台中文歌曲龙虎榜");
        topList16.setPic("http://p1.music.126.net/YQsr07nkdkOyZrlAkf0SHA==/18976471183805915.jpg");
        topListSet.add(topList16);
        TopList topList19 = new TopList();
        topList19.setIdx("19");
        topList19.setName("法国NRJ EuroHot周榜\n\n\n");
        topList19.setPic("http://p1.music.126.net/6O0ZEnO-I_RADBylVypprg==/109951162873641556.jpg");
        topListSet.add(topList19);
        TopList topList20 = new TopList();
        topList20.setIdx("20");
        topList20.setName("台湾Hito排行榜\n\n\n");
        topList20.setPic("http://p1.music.126.net/wqi4TF4ILiTUUL5T7zhwsQ==/18646617697286899.jpg");
        topListSet.add(topList20);
        TopList topList21 = new TopList();
        topList21.setIdx("21");
        topList21.setName("Beatport全球电子舞曲榜\n\n\n");
        topList21.setPic("http://p1.music.126.net/A61n94BjWAb-ql4xpwpYcg==/18613632348448741.jpg");
        topListSet.add(topList21);
    }

    public static TopListSet getTopListSet() {
        if(topListSet == null){
            topListSet = new TopListSet();
            topListSet.init();
        }
        return topListSet;
    }
}
