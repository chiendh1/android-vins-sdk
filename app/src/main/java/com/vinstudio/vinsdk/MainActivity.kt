package com.vinstudio.vinsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonMoreApp.setOnClickListener {
            startActivity(AppsActivity.getIntent(this))
        }
        ShowDialog.promo(this)
    }
}
