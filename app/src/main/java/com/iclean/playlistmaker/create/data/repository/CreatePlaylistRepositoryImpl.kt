package com.iclean.playlistmaker.create.data.repository

import com.iclean.playlistmaker.create.domain.CreatePlaylistRepository
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor



class CreatePlaylistRepositoryImpl(
    private val appDataBase : AppDatabase,
    private val dbConvertor: PlaylistDbConvertor
    )
    : CreatePlaylistRepository {

    //Сохраняем плейлист в базу
    override suspend fun insertPlaylist(playlist: Playlist) {
        appDataBase.playlistDao().insertPlaylist(dbConvertor.map(playlist))
    }

}