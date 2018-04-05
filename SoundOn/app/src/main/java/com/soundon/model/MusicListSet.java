package com.soundon.model;

import android.content.ContentValues;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 2017/11/25.
 */

public class MusicListSet extends ArrayList<MusicList> {

    private static MusicListSet musicListSet = null;
    private MusicListSet(){}
    public static MusicListSet getMusicListSet(){
        if(musicListSet == null){
            musicListSet = new MusicListSet();
            List<MusicList> musicLists = DataSupport.findAll(MusicList.class);
            for(MusicList musicList:musicLists){
                musicListSet.addOne(musicList);
            }
        }
        return musicListSet;
    }

    public boolean Empty(){
        return this.isEmpty();
    }

    public MusicList getCurrentMusic(){
        if(!musicListSet.Empty()){
            for(int i = 0; i < musicListSet.size(); i++){
                MusicList musicList = musicListSet.get(i);
                if(musicList.isCurrent())
                    return musicList;
            }
        }
        return null;
    }

    public boolean isInList(String musicId){
        if(!musicListSet.Empty()){
            for(int i = 0; i < musicListSet.size(); i++){
                MusicList musicList = musicListSet.get(i);
                if(musicList.getMusicId().equals(musicId))
                    return true;
            }
        }
        return false;
    }

    public int getCurrentPosition(){
        if(!musicListSet.Empty()){
            for(int i = 0; i < musicListSet.size(); i++){
                MusicList musicList = musicListSet.get(i);
                if(musicList.isCurrent())
                    return i;
            }
        }
        return -1;
    }

    public MusicList getNextMusic(){
        if(this.getCurrentPosition() != -1){
            if(this.getCurrentPosition() == this.size()-1)
                return this.get(0);
            else return this.get(this.getCurrentPosition()+1);
        }
        return null;
    }

    public MusicList getPrevMusic(){
        if(this.getCurrentPosition() != -1){
            if(this.getCurrentPosition() == 0)
                return this.get(this.size()-1);
            else return this.get(this.getCurrentPosition()-1);
        }
        return null;
    }

    public void setCurrentMusic(String musicId){
        for(int i = 0; i < this.size(); i++){
            if(this.get(i).getMusicId().equals(musicId))
                this.get(i).setCurrent(true);
            else this.get(i).setCurrent(false);
        }
    }

    public void addOne(MusicList one){
        this.add(one);
    }

    public void addOneToTheNext(MusicList one){
        if(!this.isInList(one.getMusicId())){
            if(this.getCurrentPosition() != -1)
                this.add(this.getCurrentPosition() + 1, one);
            else this.add(one);
        }
    }

    public boolean deleteOne(String musicId){
        int count = 0;
        for(int i = 0; i < this.size(); i++){
            if(this.get(i).getMusicId().equals(musicId)){
                this.remove(i);
                i--;
                count++;
            }
        }
        if(count > 0) return true;
        return false;
    }

    public void deleteAll(){
        this.clear();
    }

    public void saveToDB(MusicList musicList){
        if(DataSupport.where("musicId = ?", musicList.getMusicId()).count(MusicList.class) == 0){
            Log.d("database",musicList.getMusicName());
            musicList.save();
        }
    }

    public void setDBCurrent(String musicId){
        ContentValues values = new ContentValues();
        values.put("current", "0");
        DataSupport.updateAll(MusicList.class, values);
        values.put("current","1");
        DataSupport.updateAll(MusicList.class, values, "musicId = ?", musicId);
    }

    public void deleteOneFromDB(String musicId){
        DataSupport.deleteAll(MusicList.class,"musicId = ?",musicId);
    }

}
