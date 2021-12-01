package com.example.gamebox;

public class RatingData {
   private   int rating ;
   private String review;

    public RatingData(int rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public float getRating() {
        float rate = rating;
        return rate;
    }

    public String getReview() {
        return review;
    }
}
