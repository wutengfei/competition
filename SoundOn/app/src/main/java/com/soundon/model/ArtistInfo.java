package com.soundon.model;

/**
 * Created by Vince on 2017/11/25.
 */

public class ArtistInfo {

    private static ArtistInfo artistInfo = null;
    private ArtistInfo(){}
    public static ArtistInfo getArtistInfo(){
        if(artistInfo == null)
            artistInfo = new ArtistInfo();
        return artistInfo;
    }

    //    private String briefDesc;
    private String artistId;
    private String artistName;
    private String artistPic;

//    public String getBriefDesc() {
//        return briefDesc;
//    }
//
//    public void setBriefDesc(String briefDesc) {
//        this.briefDesc = briefDesc;
//    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistPic() {
        return artistPic;
    }

    public void setArtistPic(String artistPic) {
        this.artistPic = artistPic;
    }

}
