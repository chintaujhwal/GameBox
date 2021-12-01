package com.example.gamebox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.game_fragment_details, container, false);
        TextView plaforms=view.findViewById(R.id.platforms_content);
        TextView publishers=view.findViewById(R.id.publish_content);
        TextView deve=view.findViewById(R.id.developers_content);


        String parent = getArguments().getString("parent");
        Log.i("fra_req", "onCreateView: "+parent);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        DatabaseReference gameData=reference.child("games").child(parent);

        gameData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameData gameData2 = snapshot.getValue(GameData.class);
               plaforms.setText(gameData2.getPlatforms());
               publishers.setText(gameData2.getPublishers());
               deve.setText(gameData2.getDevelopers());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}