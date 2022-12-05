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

public class GameLevelsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment_levels, container, false);
        TextView level = view.findViewById(R.id.level_content);
        String parent = getArguments().getString("parent");
        Log.i("fra_req", "onCreateView: " + parent);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference gameData = reference.child("games").child(parent);

        gameData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameData gameData2 = snapshot.getValue(GameData.class);
//                level.setText(gameData2.getLevels());
                level.setText("Coastal Forest\n\nMountain Temple\n\nMountain Village\n\nBase Approach\n\nMountain Base\n\nBase Exterior\n\nCliffside Village\n\nMountain Pass\n\nChasm Monastery\n\nShanty Town");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}