package com.think.design.home

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 15:00
 * desc   :
 */
class HomeItemDecoration(@Px dividerSize: Int, @ColorInt dividerColor: Int, spanCount: Int) : ItemDecoration() {

    private val spanCount: Int
    private val dividerPaint = Paint()
    private val bounds = Rect()

    init {
        this.dividerPaint.setColor(dividerColor)
        this.dividerPaint.strokeWidth = dividerSize.toFloat()
        this.dividerPaint.style = Paint.Style.STROKE
        this.dividerPaint.isAntiAlias = true
        this.spanCount = spanCount
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0..<childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val y = bounds.bottom
            val startX = bounds.left
            val stopX = bounds.right
            canvas.drawLine(startX.toFloat(), y.toFloat(), stopX.toFloat(), y.toFloat(), dividerPaint)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val isRTL = parent.layoutDirection == View.LAYOUT_DIRECTION_RTL
        for (i in 0..<childCount) {
            val child = parent.getChildAt(i)
            if (isChildInLastColumn(parent, child)) {
                continue
            }
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val x = if (isRTL) bounds.left else bounds.right
            val startY = bounds.top
            val stopY = bounds.bottom
            canvas.drawLine(x.toFloat(), startY.toFloat(), x.toFloat(), stopY.toFloat(), dividerPaint)
        }
    }

    private fun isChildInLastColumn(parent: RecyclerView, child: View): Boolean {
        return parent.getChildAdapterPosition(child) % spanCount == spanCount - 1
    }
}