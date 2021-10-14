package com.example.gamebox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class GamePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView gamePoster = findViewById(R.id.game_poster);

        Toolbar actionBarGame = findViewById(R.id.actionbar_game);
        actionBarGame.setTitle("");
        setSupportActionBar(actionBarGame);

        ImageView backButton = actionBarGame.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(gamePoster.getDrawable() == null)
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
    }
}

class GameFragmentAdapter extends FragmentStateAdapter {

    public GameFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new RequirementsFragment();
            case 2: return new LevelsFragment();
        }
        return new DetailsFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}