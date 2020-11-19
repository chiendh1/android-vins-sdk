package com.vinstudio.vinsdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vinstudio.vinsdk.callback.VinOnClickListener
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity(), VinOnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageAds.mImgSourceCancle!!.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        imageAds.registerEventListener(this)
    }
    override fun startActivity() {

    }
}