package com.example.gamebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);

        LinearLayout ratingLayout = findViewById(R.id.ratingLayout);

        RatingBar ratingBar = (RatingBar) ratingLayout.getChildAt(0);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TextView ratingValue = (TextView) ratingLayout.getChildAt(1);
                if(rating != 0.0)
                    ratingValue.setText("Rated " + rating);
                else
                    ratingValue.setText("Rate");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_rating, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                Toast.makeText(RatingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                return true;
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }
}