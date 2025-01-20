package com.iclean.playlistmaker.media.presentation.playlists

import com.iclean.playlistmaker.create.domain.models.Playlist

data class LiveDataPlaylistState(val playlists : List<Playlist>, val status : Int?)