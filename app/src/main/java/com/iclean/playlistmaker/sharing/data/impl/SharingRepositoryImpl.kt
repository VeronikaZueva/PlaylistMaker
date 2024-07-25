package com.iclean.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.iclean.playlistmaker.sharing.data.SharingRepository

class SharingRepositoryImpl(private val context: Context) : SharingRepository {
    override fun shareApp(link : String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        context.startActivity(shareIntent)
    }

    override fun sendMessage(email : String, subject : String, message : String) {
        val sendIntent = Intent(Intent.ACTION_SENDTO)
        sendIntent.data = Uri.parse("mailto:")
        sendIntent.putExtra(Intent.EXTRA_EMAIL, email)
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(sendIntent)
    }

    override fun goToLink(policy : String) {
        val docIntent = Intent(Intent.ACTION_VIEW, Uri.parse(policy))
        context.startActivity(docIntent)
    }
}