package com.example.gamebox;

public class Userdetails {
    private String profilePic;
    private  String username;

    public Userdetails(String profilePic, String username) {
        this.profilePic = profilePic;
        this.username = username;
    }

    public Userdetails() {
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getUsername() {
        return username;
    }
}
