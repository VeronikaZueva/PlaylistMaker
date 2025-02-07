package com.iclean.playlistmaker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iclean.playlistmaker.db.entity.TrackInPlaylistEntity

@Dao
interface TrackInPlaylistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackInPlaylist(trackInPlaylistEntity: TrackInPlaylistEntity)

    @Query("SELECT * FROM track_in_playlist_table WHERE trackId IN (:trackIdList)")
    suspend fun getTracksForPlaylist(trackIdList: List<Int>?) : List<TrackInPlaylistEntity>

    @Query("SELECT * FROM track_in_playlist_table")
    suspend fun getTracksForList() : List<TrackInPlaylistEntity>

    @Query("DELETE FROM track_in_playlist_table WHERE trackId = :id ")
    suspend fun deleteTrack(id : Int)
}