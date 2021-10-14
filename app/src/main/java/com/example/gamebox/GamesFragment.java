package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        GamesAdapter adapter = new GamesAdapter(gamesList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        SpacingItemDecorator Spacing = new SpacingItemDecorator((int) (16 * (this.getResources().getDisplayMetrics().density)));
        recyclerView.addItemDecoration(Spacing);

        Button gamePage = (Button) rootView.findViewById(R.id.game_page_nav);
        gamePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), GamePage.class);
                startActivity(i);
            }
        });

        Button bookmarksPage = (Button) rootView.findViewById(R.id.bookmarks_page_nav);
        bookmarksPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), BookmarksActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}

