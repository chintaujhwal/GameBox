package com.example.gamebox;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }
}