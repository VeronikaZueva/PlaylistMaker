package com.iclean.playlistmaker

import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.SwitchCompat


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Возвращаемся домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            this.finish()
        }

        //Переключаем тему
        val sharedPreferences = getSharedPreferences(App.SETTING_PARAMS, MODE_PRIVATE)
        val themeSwitcher = findViewById<SwitchCompat>(R.id.themeSwitcher)
        themeSwitcher.isChecked = sharedPreferences.getBoolean(App.KEY_FOR_THEME, App.THEME_DEFAULT)

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            (application as App).switchTheme(checked)

        }

        //Поделиться приложением
        val shareButton = findViewById<ImageButton>(R.id.share_button)
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)

            shareIntent.type = "text/plain"
            shareIntent.putExtra(EXTRA_TEXT, resources.getString(R.string.shareMessage))
            startActivity(shareIntent)
        }

        //Написать в поддержку
        val sendButton = findViewById<ImageButton>(R.id.send_button)
        sendButton.setOnClickListener {

            val sendIntent = Intent(Intent.ACTION_SENDTO)
                sendIntent.data = Uri.parse("mailto:")
                sendIntent.putExtra(EXTRA_EMAIL, resources.getString(R.string.receiver))
                sendIntent.putExtra(EXTRA_SUBJECT, resources.getString(R.string.subject))
                sendIntent.putExtra(EXTRA_TEXT, resources.getString(R.string.mail_text))
            startActivity(sendIntent)
        }

        //Документация
        val userDocButton = findViewById<ImageButton>(R.id.user_docs)
        userDocButton.setOnClickListener {
            val docIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.link_address)))
            startActivity(docIntent)
        }

    }
}