package com.example.gamebox;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoratorGrid extends RecyclerView.ItemDecoration {

    private int spacing;

    SpacingItemDecoratorGrid(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;
        outRect.left = spacing;
    }
}
