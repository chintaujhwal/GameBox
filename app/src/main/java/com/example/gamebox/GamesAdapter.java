package com.example.gamebox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {

    ArrayList<Game> gamesList;

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView gameCard;

        MyViewHolder(View itemView) {
            super(itemView);
            gameCard = itemView.findViewById(R.id.game_card);
        }
    }

    GamesAdapter(ArrayList<Game> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Game currentGame = gamesList.get(position);
        holder.gameCard.setImageResource(currentGame.getPoster());
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
