package com.example.mybeats.view.splashscreen

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.mybeats.databinding.ActivitySplashScreenBinding
import com.example.mybeats.view.login.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    companion object {
        private const val ANIMATION_SPEED = 1.5F
    }

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFullScreen()
        setupAnimation()
    }
    private fun setupAnimation() {
        binding.splashScreenAnimation.apply {
            speed = ANIMATION_SPEED
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) = Unit
                override fun onAnimationEnd(p0: Animator?) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                override fun onAnimationCancel(p0: Animator?) = Unit
                override fun onAnimationRepeat(p0: Animator?) = Unit
            })
        }
    }
    @Suppress("DEPRECATION")
    private fun setupFullScreen() {
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}
