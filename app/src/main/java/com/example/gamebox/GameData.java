package com.example.gamebox;

import android.net.Uri;

public class GameData {
    private String posterUrl,imageUrl,title,genre,overview,platforms,developers,publishers,os,processors,memory,graphics,direct_X,storage,levels;
    private int year,rating;
public void Gamedata (String posterUrl,String imageUrl,String title,String genre,String overview,String platforms,String developers,String publishers,String os,String processors,String memory,String graphics,String direct_X,String storage,String levels,int year,int rating){
this.posterUrl= posterUrl;
this.imageUrl=imageUrl;
this.title=title;
this.genre=genre;
this.overview=overview;
this.platforms=platforms;
this.developers=developers;
this.publishers=publishers;
this.os=os;
this.processors=processors;
this.memory=memory;
this.storage=storage;
this.direct_X=direct_X;
this.levels=levels;
this.year=year;
this.rating=rating;
}

    public String getDevelopers() {
        return developers;
    }

    public String getLevels() {
        return levels;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    public String getStorage() {
        return storage;
    }

    public String getGraphics() {
        return graphics;
    }

    public String getDirect_X() {
        return direct_X;
    }

    public String getMemory() {
        return memory;
    }

    public String getProcessors() {
        return processors;
    }

    public String getOs() {
        return os;
    }

    public String getPublishers() {
        return publishers;
    }

    public Uri getPosterUrl() {
    Uri Uri= android.net.Uri.parse(posterUrl);
    return Uri;
    }

    public String getPlatforms() {
        return platforms;
    }

    public String getOverview() {
        return overview;
    }

    public String getGenre() {
        return genre;
    }

    public Uri getImageUrl() {
        Uri Uri= android.net.Uri.parse(imageUrl);
        return Uri;
    }

    public String getTitle() {
        return title;
    }
}
