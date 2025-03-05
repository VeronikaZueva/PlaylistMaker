package com.iclean.playlistmaker.sharing.domain


import com.iclean.playlistmaker.search.domain.models.Track

interface SharingRepository {
    fun shareApp(link : String)
    fun sendMessage(email : String, subject : String, message : String)
    fun goToLink(policy : String)
    fun sharePlaylist(playlistName : String, playlistDescription : String?, playlistCount : Int, trackList : List<Track>)
}