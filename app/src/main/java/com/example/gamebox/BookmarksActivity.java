package com.example.gamebox;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        GridView gridView = findViewById(R.id.bookmarksGridView);
        BookmarksAdapter adapter = new BookmarksAdapter(BookmarksActivity.this, gamesList);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

class BookmarksAdapter extends ArrayAdapter<Game> {

    public BookmarksAdapter(Context context, ArrayList<Game> games) {
        super(context, 0, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_card, parent, false);
        }

        Game currentGame = getItem(position);

        ImageView poster = convertView.findViewById(R.id.game_card);
        poster.setImageResource(currentGame.getPoster());

        return convertView;
    }
}