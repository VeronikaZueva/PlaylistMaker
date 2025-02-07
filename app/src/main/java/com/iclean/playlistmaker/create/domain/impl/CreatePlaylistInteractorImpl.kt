package com.iclean.playlistmaker.create.domain.impl


import android.net.Uri
import com.iclean.playlistmaker.create.domain.CreatePlaylistInteractor
import com.iclean.playlistmaker.create.domain.CreatePlaylistRepository
import com.iclean.playlistmaker.create.domain.models.Playlist


class CreatePlaylistInteractorImpl(
    private val createPlaylistRepository: CreatePlaylistRepository)
    : CreatePlaylistInteractor {

    override suspend fun saveImage(uri: Uri, nameFile: String): Uri {
        return createPlaylistRepository.saveImage(uri, nameFile)
    }

    //Сохраняем плейлист в БД
    override suspend fun insertPlaylist(playlist : Playlist) {
        createPlaylistRepository.insertPlaylist(playlist)
    }

    }