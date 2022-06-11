package com.example.hw_animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.hw_animation.customViews.CrossView
import com.example.hw_animation.customViews.CustomView
import com.example.hw_animation.customViews.PointView

class MainActivity : AppCompatActivity(), Animation.AnimationListener {

    lateinit var cross: CrossView
    lateinit var points: List<PointView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text: TextView = findViewById(R.id.loading_text)
        val textAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.text_alpha)
        text.startAnimation(textAnim)

        cross = findViewById(R.id.cross)
        points = arrayOf(R.id.p_up, R.id.p_right, R.id.p_down, R.id.p_left)
            .map { findViewById<PointView>(it)}

        cross.anim.setAnimationListener(this)
        for (pAnim in points.map { it.anim })
            pAnim.setAnimationListener(this)

        cross.runAnim()

    }


    override fun onAnimationStart(animation: Animation?) {}
    override fun onAnimationRepeat(animation: Animation?) {}
    override fun onAnimationEnd(animation: Animation?) {
        when (animation) {
            cross.anim -> points[0].runAnim()
            points[0].anim -> points[1].runAnim()
            points[1].anim -> points[2].runAnim()
            points[2].anim -> points[3].runAnim()
            points[3].anim -> cross.runAnim()
        }
    }

}
