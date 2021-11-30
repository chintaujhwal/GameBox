package com.example.gamebox;

import android.net.Uri;

public class Game {
    private final String image;
    private String parent;

    Game(String Poster,String parent) {
        this.image = Poster;
        this.parent=parent;
    }

    public Uri getImageUrl() {
        Uri uri=Uri.parse(image);
        return uri;
    }

    public String getParent() {
        return parent;
    }
}
