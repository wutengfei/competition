package com.soundon.util;

import android.text.TextUtils;
import android.util.Log;

import com.soundon.model.AlbumInfo;
import com.soundon.model.AlbumInfoList;
import com.soundon.model.AlbumMusic;
import com.soundon.model.AlbumMusicList;
import com.soundon.model.ArtistInfo;
import com.soundon.model.MusicUrl;
import com.soundon.model.SearchMusic;
import com.soundon.model.SearchMusicList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vince on 2017/11/25.
 */

public class JsonUtil {
    //解析和处理服务器返回的歌曲数据
    public static boolean handleMusicUrlResponse(String response){
        if(!TextUtils.isEmpty(response)){
            MusicUrl musicUrl = MusicUrl.getMusicUrl();
            try{
                JSONObject allMusic = new JSONObject(response);
                String musicDate = allMusic.optString("data");
                JSONArray musics = new JSONArray(musicDate);
                JSONObject aMusic = musics.getJSONObject(0);
                musicUrl.setUrl(aMusic.optString("url"));
                Log.d("tets",musicUrl.getUrl());
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleSearchMusicResponse(String response){
        if(!TextUtils.isEmpty(response)){
            SearchMusicList searchMusicList = SearchMusicList.getSearchMusicList();
            searchMusicList.deleteAll();
            try{
                JSONObject data = new JSONObject(response);
                String result = data.optString("result");
                JSONObject songsData = new JSONObject(result);
                String songString = songsData.optString("songs");
                JSONArray songs = new JSONArray(songString);
                for(int i = 0; i < songs.length(); i++){
                    JSONObject aSong = songs.getJSONObject(i);
                    SearchMusic searchMusic = new SearchMusic();
                    searchMusic.setMusicId(aSong.optString("id"));
                    searchMusic.setMusicName(aSong.optString("name"));
                    JSONArray artists = new JSONArray(aSong.optString("artists"));
                    JSONObject artist = artists.getJSONObject(0);
                    searchMusic.setArtistId(artist.optString("id"));
                    searchMusic.setArtistName(artist.optString("name"));
                    JSONObject album = new JSONObject(aSong.optString("album"));
                    searchMusic.setAlbumId(album.optString("id"));
                    searchMusic.setAlbumName(album.optString("name"));
                    searchMusicList.addOne(searchMusic);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleAlbumMusicResponse(String response){
        if(!TextUtils.isEmpty(response)){
            AlbumMusicList albumMusicList = AlbumMusicList.getAlbumMusicList();
            albumMusicList.deleteAll();
            try{
                JSONObject albumContent = new JSONObject(response);
                String alString = albumContent.optString("album");
                JSONObject album = new JSONObject(alString);
                String picUrl = album.optString("picUrl");
                String songString = albumContent.optString("songs");
                JSONArray songs = new JSONArray(songString);
                for(int i = 0; i < songs.length(); i++){
                    JSONObject aSong = songs.getJSONObject(i);
                    AlbumMusic albumMusic = new AlbumMusic();
                    albumMusic.setMusicId(aSong.optString("id"));
                    albumMusic.setMusicName(aSong.optString("name"));
                    JSONArray artists = new JSONArray(aSong.optString("ar"));
                    JSONObject artist = artists.getJSONObject(0);
                    albumMusic.setArtistId(artist.optString("id"));
                    albumMusic.setArtistName(artist.optString("name"));
                    albumMusic.setAlbumPic(picUrl);
                    albumMusicList.addOne(albumMusic);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleAlbumInfoResponse(String response){
        if(!TextUtils.isEmpty(response)){
            AlbumInfoList albumInfoList = AlbumInfoList.getAlbumInfoList();
            ArtistInfo artistInfo = ArtistInfo.getArtistInfo();
            albumInfoList.deleteAll();
            try{
                JSONObject data = new JSONObject(response);
                String artistString = data.optString("artist");
                JSONObject artist = new JSONObject(artistString);
                artistInfo.setArtistId(artist.optString("id"));
                artistInfo.setArtistName(artist.optString("name"));
                artistInfo.setArtistPic(artist.optString("picUrl"));
                String hotAlbumString = data.optString("hotAlbums");
                JSONArray hotAlbums = new JSONArray(hotAlbumString);
                for(int i = 0; i < hotAlbums.length(); i++){
                    JSONObject album = hotAlbums.getJSONObject(i);
                    AlbumInfo albumInfo = new AlbumInfo();
                    albumInfo.setAlbumId(album.optString("id"));
                    albumInfo.setAlbumName(album.optString("name"));
                    albumInfo.setAlbumPic(album.optString("picUrl"));
                    albumInfoList.addOne(albumInfo);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleTopListResponse(String response){
        if(!TextUtils.isEmpty(response)){
            SearchMusicList searchMusicList = SearchMusicList.getSearchMusicList();
            searchMusicList.deleteAll();
            try{
                JSONObject data = new JSONObject(response);
                String result = data.optString("playlist");
                JSONObject songsData = new JSONObject(result);
                String songString = songsData.optString("tracks");
                JSONArray songs = new JSONArray(songString);
                for(int i = 0; i < songs.length(); i++){
                    JSONObject aSong = songs.getJSONObject(i);
                    SearchMusic searchMusic = new SearchMusic();
                    searchMusic.setMusicId(aSong.optString("id"));
                    searchMusic.setMusicName(aSong.optString("name"));
                    JSONArray artists = new JSONArray(aSong.optString("ar"));
                    JSONObject artist = artists.getJSONObject(0);
                    searchMusic.setArtistId(artist.optString("id"));
                    searchMusic.setArtistName(artist.optString("name"));
                    JSONObject album = new JSONObject(aSong.optString("al"));
                    searchMusic.setAlbumId(album.optString("id"));
                    searchMusic.setAlbumName(album.optString("name"));
                    searchMusic.setAlbumPic(album.optString("picUrl"));
                    searchMusicList.addOne(searchMusic);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}

