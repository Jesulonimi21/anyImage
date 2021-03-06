package com.example.jesulonimi.anyimage;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacing extends RecyclerView.ItemDecoration {
    int space;

    public VerticalSpacing(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right=space;
        outRect.left=space;
        outRect.bottom=space;
        if(parent.getChildLayoutPosition(view)==0){
            outRect.top=space;
        }

    }
}
