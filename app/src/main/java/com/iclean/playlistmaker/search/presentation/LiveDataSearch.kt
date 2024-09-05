package com.iclean.playlistmaker.search.presentation

import com.iclean.playlistmaker.search.domain.models.Track

data class LiveDataSearch(val trackList : List<Track>?, val code : Int?, val historyList : List<Track>)