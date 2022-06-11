package com.example.hw_animation.customViews

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import com.example.hw_animation.R

class PointView : CustomView {

    var pointSize = 0f
    var pointRound = 0f

    override lateinit var anim: Animation


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    override fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val att: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.PointView, defStyleAttr, 0)
        try {
            pointSize = att.getDimension(R.styleable.PointView_point_width, 6f.dp())
            pointRound = att.getDimension(R.styleable.PointView_point_round, 2.5f.dp())
            paint.color = att.getColor(R.styleable.PointView_point_color, DEFAULT_COLOR)

            val scale = att.getFloat(R.styleable.PointView_panim_scale, 1.3f)
            anim = ScaleAnimation(1f, scale, 1f, scale, 0.5f, 0.5f)
            anim.repeatCount = 1
            anim.repeatMode = Animation.REVERSE
            anim.duration = att.getFloat(R.styleable.PointView_panim_duration, 300f).toLong()
        } finally {
            att.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            0f,
            pointSize,
            pointSize,
            pointRound,
            pointRound,
            paint
        )
    }

}