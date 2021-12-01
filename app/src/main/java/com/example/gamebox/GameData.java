package com.example.gamebox;

public class GameData {
    private String d,posterUrl,imageUrl,title,genre,overview,platforms,developers,publishers, OS, Processor, Memory, Graphics, Direct_X, Storage,levels;
    private int year,rating;



    public void Gamedata (String posterUrl, String imageUrl, String title, String genre, String overview, String platforms, String developers, String publishers, String graphics,String os, String processors, String memory, String direct_X, String storage, String levels, int year, int rating){
this.posterUrl= posterUrl;
this.Graphics =graphics;
this.imageUrl=imageUrl;
this.title=title;
this.genre=genre;
this.overview=overview;
this.platforms=platforms;
this.developers=developers;
this.publishers=publishers;
this.OS =os;
this.Processor =processors;
this.Memory =memory;
this.Storage =storage;
this.Direct_X =direct_X;
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

    public String getYear() {
        String year = Integer.toString(this.year);
        return year;
    }

    public String getRating() {
        String year = Integer.toString(this.rating);
        return year;
    }

    public String getStorage() {
        return Storage;
    }

    public String getGraphics() {
        return Graphics;
    }

    public String getDirect_X() {
        return Direct_X;
    }

    public String getMemory() {
        return Memory;
    }

    public String getProcessor() {
        return Processor;
    }

    public String getOS() {
        return OS;
    }

    public String getPublishers() {
        return publishers;
    }


    public String getPosterUrl() {
        return posterUrl;
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


    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
//    public Uri getPosterUrl() {
//    Uri Uri= android.net.Uri.parse(posterUrl);
//    return Uri;
//    }
//    public Uri getImageUrl() {
//        Uri Uri= android.net.Uri.parse(imageUrl);
//        return Uri;
//    }

