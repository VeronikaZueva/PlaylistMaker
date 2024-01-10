package com.iclean.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Возвращаемся домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}