package com.example.gamebox;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        ArrayList<NewsCard> list = new ArrayList<>();

        list.add(new NewsCard(R.drawable.game_1, "Explore A New World",
                "Shooter game in which up to one hundred players fight in a battle royale, a type of large-scale last man standing deathmatch"));
        list.add(new NewsCard(R.drawable.game_2, "You Better Go Now",
                "A visually stunning third-person action-adventure that will keep you on the edge of your seat"));
        list.add(new NewsCard(R.drawable.game_3, "Perfect Storm",
                "Battle Royale game with numerous game modes for every type of game player"));
        list.add(new NewsCard(R.drawable.game_4, "Save This World",
                "Explore distant lands, fight bigger and more awe-inspiring machines, and encounter astonishing new tribes as you return to the far-future"));
        list.add(new NewsCard(R.drawable.game_5, "Everything Lost Is Meant To Be Found",
                "Explore the intense origin story of Lara Croft and her ascent from a young woman to a hardened survivor"));
        list.add(new NewsCard(R.drawable.game_6, "Endure And Survive",
                "New and evolved gameplay systems deliver upon the life-or-death stakes of Ellie's journey through the hostile world"));

        NewsAdapter adapter = new NewsAdapter(getActivity(), list);
        ListView listView = rootView.findViewById(R.id.newsFragmentLayout);
        listView.setAdapter(adapter);

        return rootView;
    }
}

class NewsAdapter extends ArrayAdapter<NewsCard> {
    public NewsAdapter(Context context, ArrayList<NewsCard> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_card, parent, false);
        }

        NewsCard currentCard = getItem(position);

        ImageView newsImage = convertView.findViewById(R.id.newsImage);
        newsImage.setImageResource(currentCard.getImage());

        TextView newsTitle = convertView.findViewById(R.id.newsTitle);
        newsTitle.setText(currentCard.getTitle());

        TextView newsContent = convertView.findViewById(R.id.newsContent);
        newsContent.setText(currentCard.getContent());

        return convertView;
    }
}

class NewsCard {
    private final int Image;
    private final String Title;
    private final String Content;

    NewsCard(int NewsImage, String NewsTitle, String Content) {
        this.Image = NewsImage;
        this.Title = NewsTitle;
        this.Content = Content;
    }

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}

