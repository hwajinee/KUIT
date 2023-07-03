package com.example.naverwebtoon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.naverwebtoon.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSplash()
    }

    fun loadSplash(){
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            // 2초 후, MainActicity로 전환하고 나면 해당 액티비티는 종료.
        }, 2000)
    }
}