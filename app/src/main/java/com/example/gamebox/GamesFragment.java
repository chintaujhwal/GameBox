package com.example.gamebox;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GamesFragment extends Fragment {
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);

        ArrayList<Game> gameslist= new ArrayList<>();
        GamesAdapter gamesAdapter =new GamesAdapter(gameslist);
        reference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference games=reference.child("games");
        games.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameslist.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren() ){
                    String parent =snapshot1.getKey();
                    Log.i("parent", parent);
                    String imageUrl=snapshot1.child("imageUrl").getValue().toString();
                    Log.i("imageurl", imageUrl);
                    gameslist.add(new Game(imageUrl,parent));
                }
                gamesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        RecyclerView recyclerView = rootView.findViewById(R.id.gamesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(gamesAdapter);
        SpacingItemDecorator Spacing = new SpacingItemDecorator((int) getResources().getDimension(R.dimen.space));
        recyclerView.addItemDecoration(Spacing);

        return rootView;
    }
}

class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {

    ArrayList<Game> games;

    GamesAdapter(ArrayList<Game> games) {
        this.games = games;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView gameCard;
        MyViewHolder(View itemView) {
            super(itemView);
            gameCard = itemView.findViewById(R.id.game_card);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Game currentGame = games.get(position);
        Log.i("Url", "URL"+currentGame.getImageUrl());
        Picasso.get().load(currentGame.getImageUrl()).into(holder.gameCard);

        holder.gameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), GameActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("parent",currentGame.getParent());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}

class SpacingItemDecorator extends RecyclerView.ItemDecoration {

    private int spacing;

    SpacingItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = spacing;
    }
}
