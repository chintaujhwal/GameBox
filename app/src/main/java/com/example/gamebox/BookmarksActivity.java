package com.example.gamebox;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        Toolbar actionBarBookmarks = findViewById(R.id.actionbar_bookmarks);
        actionBarBookmarks.setTitle("");
        actionBarBookmarks.setContentInsetStartWithNavigation(R.drawable.close_24);
        setSupportActionBar(actionBarBookmarks);

        ImageView backButton = actionBarBookmarks.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<Game> gamesList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            gamesList.add(new Game(R.drawable.pubg));
        }

        RecyclerView bookmarksRecyclerView = findViewById(R.id.bookmarksRecyclerView);
        GamesAdapter adapter = new GamesAdapter(gamesList);

        bookmarksRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        bookmarksRecyclerView.setAdapter(adapter);

        SpacingItemDecoratorGrid Spacing = new SpacingItemDecoratorGrid((int) (8 * (getApplicationContext().getResources().getDisplayMetrics().density)));
        bookmarksRecyclerView.addItemDecoration(Spacing);
    }
}