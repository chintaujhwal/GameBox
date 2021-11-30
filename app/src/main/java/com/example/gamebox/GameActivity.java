package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView gamePoster = findViewById(R.id.game_poster);

        if (gamePoster.getDrawable() == null)
            gamePoster.setImageResource(R.drawable.image_placeholder);

        TabLayout gameTabLayout = findViewById(R.id.game_tab_layout);
        ViewPager2 gameViewPager = findViewById(R.id.game_viewpager);
        gameViewPager.setAdapter(new GameFragmentAdapter(getSupportFragmentManager(), getLifecycle()));

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
                startActivity(new Intent(GameActivity.this, RatingActivity.class));
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

    public GameFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new GameRequirementsFragment();
            case 2:
                return new GameLevelsFragment();
        }
        return new GameDetailsFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}