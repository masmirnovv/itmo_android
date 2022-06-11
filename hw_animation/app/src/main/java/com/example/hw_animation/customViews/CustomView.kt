package com.example.hw_animation.customViews

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation

abstract class CustomView(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {


    val DEFAULT_COLOR: Int = Color.parseColor("#E1E3E6")
    var paint: Paint = Paint()

    abstract var anim: Animation

    fun runAnim() {
        startAnimation(anim)
    }


    init {
        init(attrs, defStyleAttr)
    }

    abstract fun init(attrs: AttributeSet?, defStyleAttr: Int)


    private val metrics = Resources.getSystem().displayMetrics!!
    internal fun Float.dp(): Float = this * 3 //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics)

}