package com.iclean.playlistmaker.player.presentation

import com.iclean.playlistmaker.create.domain.models.Playlist

data class LiveDataPlaylist(val playlists : List<Playlist>, val status : Int?)