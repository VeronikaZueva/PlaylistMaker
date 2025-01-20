package com.iclean.playlistmaker.media.data.playlists

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import com.iclean.playlistmaker.db.entity.PlaylistEntity
import com.iclean.playlistmaker.media.domain.playlists.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl(
    private val appDataBase : AppDatabase,
    private val dbConvertor: PlaylistDbConvertor
) : PlaylistRepository {
    //Выбираем плейлисты
    override fun getPlaylists(): Flow<List<Playlist>> = flow {
        val playlists = withContext(Dispatchers.IO) {
            appDataBase.playlistDao().getPlaylists()
        }
        emit(convertFromPlaylistEntity(playlists))
    }

    private fun convertFromPlaylistEntity(playlists : List<PlaylistEntity>) : List<Playlist> {
        return playlists.map {playlist -> dbConvertor.map(playlist)}
    }
}