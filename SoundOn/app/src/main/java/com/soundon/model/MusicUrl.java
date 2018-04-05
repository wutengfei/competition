package com.soundon.model;

/**
 * Created by Vince on 2017/11/25.
 */

public class MusicUrl {
    private static MusicUrl musicUrl = null;
    private MusicUrl(){}
    public static MusicUrl getMusicUrl(){
        if(musicUrl == null)
            musicUrl = new MusicUrl();
        return musicUrl;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
