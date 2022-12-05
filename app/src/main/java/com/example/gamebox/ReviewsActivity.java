package com.example.gamebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ReviewCard> list = new ArrayList<>();

        list.add(new ReviewCard(R.drawable.face, "Emma Stone", 5 ,
                "There were a few things I like about this game, although it fades compared to the fantastic experience I had"));
        list.add(new ReviewCard(R.drawable.face, "Emma Stone", 3 ,
                "There were a few things I like about this game, although it fades compared to the fantastic experience I had"));
        list.add(new ReviewCard(R.drawable.face, "Emma Stone", 4 ,
                "There were a few things I like about this game, although it fades compared to the fantastic experience I had"));
        list.add(new ReviewCard(R.drawable.face, "Emma Stone", 4 ,
                "There were a few things I like about this game, although it fades compared to the fantastic experience I had"));
        list.add(new ReviewCard(R.drawable.face, "Emma Stone", 4 ,
                "There were a few things I like about this game, although it fades compared to the fantastic experience I had"));

        ReviewAdapter adapter = new ReviewAdapter(ReviewsActivity.this, list);
        ListView listView = findViewById(R.id.reviews);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

class ReviewAdapter extends ArrayAdapter<ReviewCard> {
    public ReviewAdapter(Context context, ArrayList<ReviewCard> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_card, parent, false);
        }

        ReviewCard currentCard = getItem(position);

        ImageView userImage = convertView.findViewById(R.id.review_user_image);
        userImage.setImageResource(currentCard.getUserImage());

        TextView userName = convertView.findViewById(R.id.review_user_name);
        userName.setText(currentCard.getUserName());

        float userRating = currentCard.getUserRating();

        LinearLayout ratingArea = convertView.findViewById(R.id.rating_area);
        TextView ratingValue = (TextView) ratingArea.getChildAt(0);
        ratingValue.setText(Float.toString(userRating));

        RatingBar ratingBar = (RatingBar) ratingArea.getChildAt(1);
        ratingBar.setRating(userRating);

        TextView ratingContent = convertView.findViewById(R.id.review_user_content);
        ratingContent.setText(currentCard.getUserContent());

        return convertView;
    }
}

class ReviewCard {
    private final int UserImage;
    private final String UserName;
    private final float UserRating;
    private final String UserContent;

    ReviewCard(int UserImage, String UserName, float UserRating, String UserContent) {
        this.UserImage = UserImage;
        this.UserName = UserName;
        this.UserRating = UserRating;
        this.UserContent = UserContent;
    }

    public int getUserImage() {
        return UserImage;
    }

    public String getUserName() {
        return UserName;
    }

    public float getUserRating() {
        return UserRating;
    }

    public String getUserContent() {
        return UserContent;
    }
}

