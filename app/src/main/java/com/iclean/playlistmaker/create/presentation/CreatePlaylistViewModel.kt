package com.iclean.playlistmaker.create.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.iclean.playlistmaker.create.domain.CreatePlaylistInteractor
import com.iclean.playlistmaker.create.domain.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class CreatePlaylistViewModel(private val createPlaylistInteractor: CreatePlaylistInteractor)
    : ViewModel() {

        //Сохранить файл в хранилище приложения
        suspend fun saveImage(uri : Uri, nameFile : String) : Uri {
           return withContext(Dispatchers.IO) {
                createPlaylistInteractor.saveImage(uri, nameFile)
            }
        }

        //Сохраняем плейлист в базу данных
        suspend fun insertPlaylist(playlistName : String, playlistDescription : String?, playlistUri : String?) {
            createPlaylistInteractor.insertPlaylist(
                Playlist(
                    id = 0,
                    playlistName = playlistName,
                    playlistDescription = playlistDescription,
                    plailistImage = playlistUri,
                    playlistList = "",
                    playlistCount = 0
                )
            )
        }

}