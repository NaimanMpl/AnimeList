package fr.zelphix.animelist.adapter

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class AnimeItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom = 20
    }

}