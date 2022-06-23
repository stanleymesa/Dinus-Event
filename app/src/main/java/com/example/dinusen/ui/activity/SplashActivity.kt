package com.example.dinusen.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.dinusen.R
import com.example.dinusen.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setFadeIn()
        goToMain()
    }

    private fun setFadeIn() {
        val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in)
        binding.ivLogo.animation = animFadeIn
        binding.tvDinus.animation = animFadeIn
    }

    private fun goToMain() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}