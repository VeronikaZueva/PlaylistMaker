package com.iclean.playlistmaker.search.domain.api

import com.iclean.playlistmaker.search.domain.models.Track

interface TrackClick {
    fun getTrack(track: Track)
}