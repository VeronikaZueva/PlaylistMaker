package com.iclean.playlistmaker.playlist.data

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import com.iclean.playlistmaker.playlist.domain.PlaylistItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistItemRepositoryImpl(
    private val appDataBase : AppDatabase,
    private val dbConvertor: PlaylistDbConvertor
) : PlaylistItemRepository{

    override fun getPlaylistFromId(id: Int): Flow<Playlist> {
       return appDataBase.playlistDao().getPlaylistFromId(id).map(dbConvertor::map)
    }
}