package com.soundon.model;

import java.util.ArrayList;

/**
 * Created by Vince on 2017/11/25.
 */

public class AlbumInfoList extends ArrayList<AlbumInfo> {
    private static AlbumInfoList albumInfoList = null;
    private AlbumInfoList(){}
    public static AlbumInfoList getAlbumInfoList(){
        if(albumInfoList == null)
            albumInfoList = new AlbumInfoList();
        return albumInfoList;
    }
    public void addOne(AlbumInfo one){
        this.add(one);
    }
    public void deleteAll(){
        this.clear();
    }
}
