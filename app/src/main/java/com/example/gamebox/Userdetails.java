package com.example.gamebox;

public class Userdetails {
    private String profilePic;
    private  String username;
    private String email;

    public Userdetails(String profilePic, String username, String email) {
        this.profilePic = profilePic;
        this.username = username;
        this.email = email;
    }

    public Userdetails() {
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getUsername() {
        return username;
    }
}
