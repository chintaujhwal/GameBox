package com.example.gamebox;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

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

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        HashMap<String,Object> hashMap= new HashMap<>();
        Bundle bundle = getIntent().getExtras();
        String parent=bundle.getString("parent");
        reference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference gamadata=reference.child("games").child(parent);

        gamadata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    hashMap.put(snapshot1.getKey(),snapshot1.getValue());
                    Log.i(snapshot1.getKey(), snapshot1.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: finish();
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