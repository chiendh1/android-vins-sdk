package com.vinstudio.vinsdk

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonMoreApp.setOnClickListener {
            startActivity(AppsActivity.getIntent(this, "" + BuildConfig.APPLICATION_ID))
        }

        buttonPromo.setOnClickListener {
            ShowDialog.promo(this, "" + BuildConfig.APPLICATION_ID)
        }
    }
}
