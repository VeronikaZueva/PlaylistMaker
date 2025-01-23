package com.iclean.playlistmaker.create.domain

import com.iclean.playlistmaker.create.domain.models.Playlist

interface CreatePlaylistRepository {

    suspend fun insertPlaylist(playlist : Playlist)


}
