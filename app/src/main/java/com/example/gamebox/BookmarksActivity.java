package com.example.gamebox;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Game> gamesList = new ArrayList<>();

        GridView gridView = findViewById(R.id.bookmarksGridView);
        BookmarksAdapter adapter = new BookmarksAdapter(BookmarksActivity.this, gamesList);
        gridView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        DatabaseReference bookmarks = reference.child("users").child(auth.getCurrentUser().getUid()).child("bookmarks");
        bookmarks.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gamesList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren() ){
                    String parent =snapshot1.getKey();
                    Log.i("parent", parent);
                    String imageUrl=snapshot1.child("imageUrl").getValue().toString();
                    Log.i("imageurl", imageUrl);
                    gamesList.add(new Game(imageUrl,parent));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        Picasso.get().load(currentGame.getImageUrl()).fit().into(poster);

        return convertView;
    }
}