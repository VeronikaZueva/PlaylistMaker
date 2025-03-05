package com.iclean.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.iclean.playlistmaker.general.PlaylistMethods
import com.iclean.playlistmaker.general.TrackMethods
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.sharing.domain.SharingRepository

class SharingRepositoryImpl(private val context: Context) : SharingRepository {
    private val playlistMethods = PlaylistMethods()
    private val trackMethods = TrackMethods()

    override fun shareApp(link : String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(shareIntent)
    }

    override fun sendMessage(email : String, subject : String, message : String) {
        val sendIntent = Intent(Intent.ACTION_SENDTO)
        sendIntent.data = Uri.parse("mailto:")
        sendIntent.putExtra(Intent.EXTRA_EMAIL, email)
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(sendIntent)
    }

    override fun goToLink(policy : String) {
        val docIntent = Intent(Intent.ACTION_VIEW, Uri.parse(policy))
        docIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(docIntent)
    }

    override fun sharePlaylist(playlistName : String, playlistDescription : String?, playlistCount : Int, trackList : List<Track>) {
        //Шаблон сообщения
        val count = playlistMethods.setCountTrack(playlistCount)
        val description = isDescription(playlistDescription)
        val text = "Плейлист ${playlistName}\n" + description + count + ": \n\n" + addTrackList(trackList)

        shareApp(text)

    }

    private fun isDescription(descritpion : String?) : String {
           return if(descritpion!=null) {"${descritpion}\n"} else {"\n"}
    }
    private fun addTrackList(tracks : List<Track>) : String {
        var list = ""
        tracks.forEachIndexed { index, track ->
            val newIndex = index + 1
            list = list + newIndex + ". " + track.artistName + " - " + track.trackName + " (" + trackMethods.dateFormatTrack(track.trackTimeMillis) + ") \n"
        }

        return list
    }
}