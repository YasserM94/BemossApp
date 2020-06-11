package com.bau.bemoss.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {
    private val splayTimeOut: Long = 1200 // 1.2 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initActivity()
    }

    private fun initActivity() {
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, DashboardActivity::class.java))

            // close this activity
            finish()
        }, splayTimeOut)

    }
}
