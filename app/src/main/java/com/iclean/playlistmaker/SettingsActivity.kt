package com.iclean.playlistmaker

import android.content.Intent
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
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

        //Поделиться приложением
        val shareButton = findViewById<ImageButton>(R.id.share_button)
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val messageShare = getString(R.string.shareMessage)

            shareIntent.type = "text/plain"
            shareIntent.putExtra(EXTRA_TEXT, messageShare)
            startActivity(shareIntent)
        }

        //Написать в поддержку
        val sendButton = findViewById<ImageButton>(R.id.send_button)
        sendButton.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SENDTO)
            val receiver = getString(R.string.receiver)
            val subject = getString(R.string.subject)
            val messageMail = getString(R.string.mail_text)

            sendIntent.data= Uri.parse(receiver)
            sendIntent.putExtra(EXTRA_SUBJECT, subject)
            sendIntent.putExtra(EXTRA_TEXT, messageMail)
            startActivity(sendIntent)
        }

        //
        val userDocButton = findViewById<ImageButton>(R.id.user_docs)
        userDocButton.setOnClickListener {
            val linkAddress = getString(R.string.link_address)
            val docIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkAddress))
            startActivity(docIntent)
        }

    }
}