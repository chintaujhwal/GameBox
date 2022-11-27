package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private String userUID;

    private TextView mUsername;
    private TextView mUseremail;
    private ImageView mProfilPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        auth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        userUID=auth.getCurrentUser().getUid();

        TabLayout homeTabLayout = findViewById(R.id.home_tab_layout);
        ViewPager2 homeViewPager = findViewById(R.id.home_view_pager);
        homeViewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(), getLifecycle()));

        homeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        homeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                homeTabLayout.selectTab(homeTabLayout.getTabAt(position));
            }
        });

        Toolbar customToolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(customToolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, customToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        View header= navigationView.getHeaderView(0);
        mUsername =header.findViewById(R.id.username);
        mUseremail = header.findViewById(R.id.email);
        mProfilPic=header.findViewById(R.id.profilePic_main);

        if(userUID!=null){
            setUser(userUID);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        return true;
                    case R.id.search:
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        return true;
                    case R.id.bookmarks:
                        startActivity(new Intent(MainActivity.this, BookmarksActivity.class));
                        return true;
                    case R.id.logout:
                        auth.signOut();
                        startActivity(new Intent(MainActivity.this,StartActivity.class));
                        finish();
                        Toast.makeText(MainActivity.this, "Good Bye", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
    }

    private void setUser(String userUID) {
        reference.child("users").child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdetails obj = snapshot.getValue(Userdetails.class);
                assert obj != null;
                Picasso.get().load(obj.getProfilePic()).placeholder(R.drawable.profile_24).into(mProfilPic);
                String username= obj.getUsername();
                mUsername.setText(username);
                String email = obj.getEmail();
                mUseremail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                startActivity(new Intent(this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

class HomeFragmentAdapter extends FragmentStateAdapter {

    public HomeFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new NewsFragment();
            case 2:
                return new ComingSoonFragment();
        }
        return new GamesFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}