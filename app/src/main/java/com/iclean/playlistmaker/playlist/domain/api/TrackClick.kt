package com.iclean.playlistmaker.playlist.domain.api

import com.iclean.playlistmaker.search.domain.models.Track

interface TrackClick {
    fun getTrack(track: Track)
    fun removeTrack(track: Int)
}