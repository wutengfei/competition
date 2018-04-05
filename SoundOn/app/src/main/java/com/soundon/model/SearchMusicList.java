package com.soundon.model;

import java.util.ArrayList;

/**
 * Created by Vince on 2017/11/25.
 */

public class SearchMusicList extends ArrayList<SearchMusic> {

    private static SearchMusicList searchMusicList = null;

    private SearchMusicList(){}

    public static SearchMusicList getSearchMusicList(){
        if(searchMusicList == null)
            searchMusicList = new SearchMusicList();
        return searchMusicList;
    }

    public void addOne(SearchMusic one){
        this.add(one);
    }

    public void deleteAll(){
        this.clear();
    }

}
