package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DatabaseReference reference;
        ImageView game_poster = findViewById(R.id.game_poster);
        ImageView game_hero=findViewById(R.id.game_hero);
        TextView game_title=findViewById(R.id.game_title);
        TextView game_genre=findViewById(R.id.game_genre);
        TextView games_description=findViewById(R.id.game_description);
        TextView game_year=findViewById(R.id.game_year);
        View game_rating= findViewById(R.id.game_rating);
        RatingBar ratingBar =game_rating.findViewById(R.id.game_ratingbar);
        TextView rating_textview=game_rating.findViewById(R.id.rating_textview);


        Bundle bundle = getIntent().getExtras();
        String parent = bundle.getString("parent");

        Log.i("parent", "onCreate: "+parent);

//        Map<String,Object> gamemap=new HashMap<String,Object>();
//        ArrayMap<String,Object> gamemap=new ArrayMap<String,Object>();\


        reference= FirebaseDatabase.getInstance().getReference();

        DatabaseReference gameData=reference.child("games").child(parent);

        gameData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameData gameData1 = snapshot.getValue(GameData.class);
                Picasso.get().load(gameData1.getImageUrl()).into(game_poster);
                Picasso.get().load(gameData1.getPosterUrl()).fit().into(game_hero);
                game_title.setText(gameData1.getTitle());
                game_genre.setText(gameData1.getGenre());
                game_year.setText(gameData1.getYear());
                games_description.setText(gameData1.getOverview());
                ratingBar.setRating(Float.parseFloat(gameData1.getRating()));
                rating_textview.setText(gameData1.getRating());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        Bundle fragBundle = new Bundle();
        fragBundle.putString("parent",parent);



        TabLayout gameTabLayout = findViewById(R.id.game_tab_layout);
        ViewPager2 gameViewPager = findViewById(R.id.game_viewpager);
        gameViewPager.setAdapter(new GameFragmentAdapter(getSupportFragmentManager(), getLifecycle(),fragBundle));

        gameTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                gameViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        gameViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                gameTabLayout.selectTab(gameTabLayout.getTabAt(position));
            }
        });



//        Like, Rate and Bookmark

        LinearLayout like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView favoriteIcon = (ImageView) like.getChildAt(0);
                if (favoriteIcon.getColorFilter() == null)
                    favoriteIcon.setColorFilter(getResources().getColor(R.color.orange));
                else
                    favoriteIcon.setColorFilter(null);
            }
        });

        LinearLayout rate = findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameActivity.this, RatingActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("parent",parent);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        ImageView bookmark = findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmark.getColorFilter() == null) {
                    bookmark.setColorFilter(getResources().getColor(R.color.orange));
                    Toast.makeText(GameActivity.this, "Bookmarked", Toast.LENGTH_SHORT).show();
                } else {
                    bookmark.setColorFilter(null);
                    Toast.makeText(GameActivity.this, "Bookmark Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

class GameFragmentAdapter extends FragmentStateAdapter {
private Bundle bundle;
    public GameFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle,Bundle bundle) {
        super(fragmentManager, lifecycle);
        this.bundle=bundle;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                GameRequirementsFragment obj0= new GameRequirementsFragment();
                obj0.setArguments(bundle);
                return obj0;


            case 2:
                GameLevelsFragment obj2=new GameLevelsFragment();
                 obj2.setArguments(bundle);
                return obj2;
        }
        GameDetailsFragment obj1=new GameDetailsFragment();
        obj1.setArguments(bundle);
        return obj1;
//return new GameDetailsFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}