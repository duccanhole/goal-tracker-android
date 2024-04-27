package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1800 // 1.8 seconds delay
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        scope.launch {
            delay(SPLASH_DELAY)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Cancel the CoroutineScope when the activity is destroyed to avoid memory leaks
    }
}