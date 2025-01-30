package com.iclean.playlistmaker.playlist.data

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import com.iclean.playlistmaker.db.entity.TrackInPlaylistEntity
import com.iclean.playlistmaker.playlist.domain.PlaylistItemRepository
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PlaylistItemRepositoryImpl(
    private val appDataBase : AppDatabase,
    private val dbConvertor: PlaylistDbConvertor
) : PlaylistItemRepository{

    override fun getPlaylistFromId(id: Int): Flow<Playlist> {
       return appDataBase.playlistDao().getPlaylistFromId(id).map(dbConvertor::map)
    }

    override fun getTracksForPlaylist(trackIdList: List<Int>?) : Flow<List<Track>> = flow {
        val tracklists = withContext(Dispatchers.IO) {
            appDataBase.trackInPlaylistDao().getTracksForPlaylist(trackIdList)
        }
        emit(convertFromTrackPlaylistEntity(tracklists))

    }


    override suspend fun checkTrackAllPlaylist(track: Int, playlistId : Int) {
        val playlists = appDataBase.playlistDao().getPlaylists().map(dbConvertor::map)
        val newPlaylist = playlists.filter{it.id != playlistId}
        val trackList = newPlaylist.filter{playlist -> playlist.playlistList!!.contains(track.toString())}
        if(trackList.isEmpty()) {
            removeTrack(track)
        }
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        appDataBase.playlistDao().deletePlaylist(playlistId)
    }

    private fun convertFromTrackPlaylistEntity(tracklists: List<TrackInPlaylistEntity>): List<Track> {
        return tracklists.map {tracklist -> dbConvertor.map(tracklist)}
    }

    private suspend fun removeTrack(track: Int) {
        appDataBase.trackInPlaylistDao().deleteTrack(track)
    }

}