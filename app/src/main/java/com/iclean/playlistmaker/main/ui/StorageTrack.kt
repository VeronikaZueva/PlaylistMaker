package com.iclean.playlistmaker.main.ui

interface StorageTrack {
    fun getCurrentTrack() : String
    fun setCurrentTrack(track : String)
}