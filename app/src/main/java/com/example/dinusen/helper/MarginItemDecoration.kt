package com.example.dinusen.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val margin: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {

        with(outRect) {
            right = margin / 2
            left = margin / 2
            top = margin
            bottom = margin

        }
    }
}