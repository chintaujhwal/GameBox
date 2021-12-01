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

public class GameRequirementsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.game_fragment_requirements, container, false);
        TextView os=view.findViewById(R.id.os_content);
        TextView processor=view.findViewById(R.id.processor_content);
        TextView memory=view.findViewById(R.id.memory_content);
        TextView graphics=view.findViewById(R.id.graphics_content);
        TextView directX=view.findViewById(R.id.directX_content);
        TextView storage =view.findViewById(R.id.storage_content);

                String parent = getArguments().getString("parent");
        Log.i("fra_req", "onCreateView: "+parent);

       DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        DatabaseReference gameData=reference.child("games").child(parent);

        gameData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameData gameData2 = snapshot.getValue(GameData.class);
              os.setText(gameData2.getOS());
              processor.setText(gameData2.getProcessor());
              memory.setText(gameData2.getMemory());
              graphics.setText(gameData2.getGraphics());
              directX.setText(gameData2.getDirect_X());
              storage.setText(gameData2.getStorage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;


    }
}
