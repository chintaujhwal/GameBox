package com.example.gamebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        LinearLayout ratingLayout = findViewById(R.id.ratingLayout);

        RatingBar ratingBar = (RatingBar) ratingLayout.getChildAt(0);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TextView ratingValue = (TextView) ratingLayout.getChildAt(1);
                ratingValue.setText("Rated " + rating);
            }
        });
    }
}