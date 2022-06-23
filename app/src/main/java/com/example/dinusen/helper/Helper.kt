package com.example.dinusen.helper

import android.R.attr.state_focused
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.R.attr.colorPrimary
import com.google.android.material.R.attr.colorSecondaryVariant

fun Context.getHelperDrawable(drawable: Int): Drawable {
    return ContextCompat.getDrawable(this, drawable)!!
}

@ColorInt
fun Context.getColorFromAttr(@AttrRes attrColor: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun Context.getColorStateListSecondaryVariant(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(-state_focused),
            intArrayOf(state_focused),
        ),

        intArrayOf(
            this.getColorFromAttr(colorSecondaryVariant),
            this.getColorFromAttr(colorSecondaryVariant)
        )
    )
}

fun Context.getColorStateListPrimary(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(-state_focused),
            intArrayOf(state_focused),
        ),

        intArrayOf(
            this.getColorFromAttr(colorPrimary),
            this.getColorFromAttr(colorPrimary)
        )
    )
}

fun Context.getColorStateListHelper(color: Int): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(-state_focused),
            intArrayOf(state_focused),
        ),

        intArrayOf(
            ContextCompat.getColor(this, color),
            ContextCompat.getColor(this, color)
        )
    )
}

fun Int.toPixel(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}