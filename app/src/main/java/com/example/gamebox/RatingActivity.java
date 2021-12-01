package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);

        Bundle bundle = getIntent().getExtras();
        String parent = bundle.getString("parent");
        String UserUid= FirebaseAuth.getInstance().getCurrentUser().getUid();


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        LinearLayout ratingLayout = findViewById(R.id.ratingLayout);
        LinearLayout gameinfo =findViewById(R.id.Ratinggame);

        ImageView poster=gameinfo.findViewById(R.id.game_poster);
        TextView title=gameinfo.findViewById(R.id.game_title);
        TextView year =gameinfo.findViewById(R.id.game_year);
        RatingBar ratingBar = (RatingBar) ratingLayout.getChildAt(0);
        EditText review= findViewById(R.id.reviewEdittext);

        DatabaseReference rating = reference.child("rating").child(UserUid).child(parent);

        DatabaseReference rated= rating.child("rated");

//        rated.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value= snapshot.getValue(String.class);
//                if (value!=null){
//                    rating.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            RatingData ratingData= snapshot.getValue(RatingData.class);
//                            ratingBar.setRating(ratingData.getRating());
//                            review.setText(ratingData.getReview());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        DatabaseReference gamedata = reference.child("games").child(parent);
        gamedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameData gameData= snapshot.getValue(GameData.class);
                Picasso.get().load(gameData.getImageUrl()).fit().into(poster);
                title.setText(gameData.getTitle());
                year.setText(gameData.getYear());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                savedata();
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void savedata() {
        LinearLayout ratingLayout = findViewById(R.id.ratingLayout);
        RatingBar ratingBar = (RatingBar) ratingLayout.getChildAt(0);
        float rating= ratingBar.getRating();
        EditText review= findViewById(R.id.reviewEdittext);

        String UserUid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String txt_review=review.getText().toString();

        Bundle bundle = getIntent().getExtras();
        String parent = bundle.getString("parent");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rating").child(parent);
        HashMap <String,Object> map = new HashMap<>();
        map.put("rating",rating);
        map.put("rated",true);
        map.put("review",txt_review);
        map.put("UserUID",UserUid);
        reference.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               Toast.makeText(RatingActivity.this,"saved",Toast.LENGTH_SHORT).show();
               Intent intent =new Intent(RatingActivity.this,GameActivity.class);
               Bundle bundle1 = getIntent().getExtras();
               bundle1.putString("parent",parent);
               intent.putExtras(bundle1);
               startActivity(intent);
               finish();
           }
       });
    }
}