package com.nexgen.flexiBank

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var flexiBankText: TextView
    private lateinit var orangeDrop: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        flexiBankText = findViewById(R.id.flexiBankText)
        orangeDrop = findViewById(R.id.orangeDrop)

        startFlexiBankAnimation()
    }

    private fun startFlexiBankAnimation() {
        // 1. Text Fade Out Animation
        val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.text_fade_out)
        flexiBankText.startAnimation(fadeOutAnimation)

        // Listen for the end of the fade-out animation to start the drop
        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Make flexiBankText completely gone if fillAfter isn't enough or for clarity
//                 flexiBankText.visibility = View.VISIBLE

//                // 2. Start Orange Drop Animation
//                startOrangeDropAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun startOrangeDropAnimation() {
        // Make the drop visible before animating
        orangeDrop.visibility = View.VISIBLE

        val dropAnim = AnimationUtils.loadAnimation(this, R.anim.drop_animation)
        orangeDrop.startAnimation(dropAnim)

        // Optional: If you want to do something after the drop animation
        dropAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                // Drop animation finished
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}