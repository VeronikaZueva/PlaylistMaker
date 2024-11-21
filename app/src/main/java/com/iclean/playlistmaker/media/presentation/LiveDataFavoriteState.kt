package com.iclean.playlistmaker.media.presentation

import com.iclean.playlistmaker.search.domain.models.Track

data class LiveDataFavoriteState(val tracks : List<Track>, val error: Int?)