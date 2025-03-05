package com.iclean.playlistmaker.create.domain

import android.net.Uri
import com.iclean.playlistmaker.create.domain.models.Playlist

interface CreatePlaylistRepository {

    suspend fun insertPlaylist(playlist : Playlist)

    suspend fun saveImage(uri : Uri, nameFile : String) : Uri


}
