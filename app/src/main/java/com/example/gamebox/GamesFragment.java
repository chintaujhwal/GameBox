package com.example.gamebox;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GamesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);

        ArrayList<Game> gamesList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            gamesList.add(new Game(R.drawable.pubg));
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.gamesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new GamesAdapter(gamesList));

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
        holder.gameCard.setImageResource(currentGame.getPoster());
        holder.gameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), GameActivity.class));
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


