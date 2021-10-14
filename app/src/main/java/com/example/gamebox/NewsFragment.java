package com.example.gamebox;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_news, container, false);

        ArrayList<NewsCard> list = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            list.add(new NewsCard(R.drawable.pubg, "Explore a new world", "The Game is a 2009 third-person action video game based on James Cameron's 2009 film Avatar."));
        }

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
        if(convertView == null) {
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
    private int Image;
    private String Title, Content;

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


