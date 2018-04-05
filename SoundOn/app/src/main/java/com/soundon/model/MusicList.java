package com.soundon.model;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import com.soundon.util.HttpUtil;
import com.soundon.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Vince on 2017/11/25.
 */

public class MusicList extends DataSupport {

    private String musicId;
    private String musicName;
    private String artistName;
    private String albumName;
    private String albumPic;
    private boolean current;

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }

//    public void getAlbumPicFromInternet(final MusicList musicList){
//        try {
//            String Url = "http://39.106.8.190:3000/album?id=" + musicList.getAlbumId();
//            HttpUtil.sendOkHttpRequest(Url, new okhttp3.Callback() {
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    //得到服务器返回的具体内容
//                    final String responseData = response.body().string();
//                    String alPicUrl = "null";
//                    if(!TextUtils.isEmpty(responseData)){
//                        try{
//                            JSONObject albumData = new JSONObject(responseData);
//                            String album = albumData.optString("album");
//                            JSONObject aMusic = new JSONObject(album);
//                            alPicUrl = aMusic.optString("picUrl");
//                            Log.d("aa",alPicUrl);
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                    musicList.setAlbumPic(alPicUrl);
//                }
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

}
