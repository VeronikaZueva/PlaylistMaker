package com.iclean.playlistmaker.create.domain.models

data class Playlist(
    val id : Int?,
    val playlistName : String,
    val playlistDescription : String?,
    val plailistImage : String?,
    val playlistList: String?,
    val playlistCount : Int = 0
)