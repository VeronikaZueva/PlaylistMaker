package com.iclean.playlistmaker.create.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.iclean.playlistmaker.create.domain.CreatePlaylistInteractor
import com.iclean.playlistmaker.create.domain.models.Playlist

open class CreatePlaylistViewModel(private val createPlaylistInteractor: CreatePlaylistInteractor)
    : ViewModel() {

        //Сохранить файл в хранилище приложения
        suspend fun saveImage(uri : Uri, nameFile : String) : Uri {
           val uriPlay = createPlaylistInteractor.saveImage(uri, nameFile)
            Log.i("URI - ViewModel", uriPlay.toString())
            return uriPlay
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