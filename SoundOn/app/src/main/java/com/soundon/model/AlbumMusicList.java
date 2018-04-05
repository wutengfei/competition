package com.soundon.model;

import java.util.ArrayList;

/**
 * Created by Vince on 2017/11/25.
 */

public class AlbumMusicList extends ArrayList<AlbumMusic> {
    private static AlbumMusicList albumMusicList = null;
    private AlbumMusicList(){

    }
    public static AlbumMusicList getAlbumMusicList(){
        if(albumMusicList == null)
            albumMusicList = new AlbumMusicList();
        return albumMusicList;
    }
    public void addOne(AlbumMusic one){
        this.add(one);
    }
    public void deleteAll(){
        this.clear();
    }
}
