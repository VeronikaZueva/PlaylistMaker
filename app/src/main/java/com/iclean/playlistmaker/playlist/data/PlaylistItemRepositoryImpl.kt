package com.iclean.playlistmaker.playlist.data

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import com.iclean.playlistmaker.db.entity.TrackInPlaylistEntity
import com.iclean.playlistmaker.playlist.domain.PlaylistItemRepository
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistItemRepositoryImpl(
    private val appDataBase : AppDatabase,
    private val dbConvertor: PlaylistDbConvertor
) : PlaylistItemRepository {

    override fun getPlaylistFromId(id: Int): Flow<Playlist> {
        return appDataBase.playlistDao().getPlaylistFromId(id).map(dbConvertor::map)
    }

    override suspend fun getTracksForPlaylist(trackIdList: String): List<Track> {

        val entityList = appDataBase.trackInPlaylistDao().getTracksForList()
        val trackList = convertFromTrackPlaylistEntity(entityList)
        val mutableList = mutableListOf<Track>()

        val array = trackIdList.split(", ").mapNotNull { it.toIntOrNull() }
        for(item in array) {
            trackList.map {
                if(it.trackId == item.toString()) {
                    mutableList.add(it)
                }
            }

        }
        val result : List<Track> = mutableList
        return result
    }


    override suspend fun checkTrackAllPlaylist(track: Int, playlistId: Int) {
        val playlists = appDataBase.playlistDao().getPlaylists().map(dbConvertor::map)
        val newPlaylist = playlists.filter { it.id != playlistId }
        val trackList =
            newPlaylist.filter { playlist -> playlist.playlistList!!.contains(track.toString()) }
        if (trackList.isEmpty()) {
            removeTrack(track)
        }
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        appDataBase.playlistDao().deletePlaylist(playlistId)
    }

    private fun convertFromTrackPlaylistEntity(tracklists: List<TrackInPlaylistEntity>): List<Track> {
        return tracklists.map { tracklist -> dbConvertor.map(tracklist) }
    }

    private suspend fun removeTrack(track: Int) {
        appDataBase.trackInPlaylistDao().deleteTrack(track)
    }

}