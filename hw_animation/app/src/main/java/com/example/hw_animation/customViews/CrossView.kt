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
import android.view.animation.RotateAnimation
import androidx.core.view.marginRight
import com.example.hw_animation.MainActivity
import com.example.hw_animation.R

class CrossView : CustomView {

    var crossLen = 22f.dp()
    var crossWidth = 0f
    var crossRound = 0f

    override lateinit var anim: Animation


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    override fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val att: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CrossView, defStyleAttr, 0)
        try {
            crossWidth = att.getDimension(R.styleable.CrossView_cross_width, 6f.dp())
            crossRound = att.getDimension(R.styleable.CrossView_cross_round, 2.5f.dp())
            paint.color = att.getColor(R.styleable.CrossView_cross_color, DEFAULT_COLOR)

            anim = RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = att.getFloat(R.styleable.CrossView_cranim_duration, 300f).toLong()
            anim.startOffset = att.getFloat(R.styleable.CrossView_cranim_offset, 1000f).toLong()
        } finally {
            att.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            (crossLen - crossWidth) / 2,
            crossLen,
            (crossLen + crossWidth) / 2,
            crossRound,
            crossRound,
            paint
        )
        canvas.drawRoundRect(
            (crossLen - crossWidth) / 2,
            0f,
            (crossLen + crossWidth) / 2,
            crossLen,
            crossRound,
            crossRound,
            paint
        )
    }

}