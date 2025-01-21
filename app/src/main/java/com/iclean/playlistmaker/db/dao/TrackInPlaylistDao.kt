package com.iclean.playlistmaker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.iclean.playlistmaker.db.entity.TrackInPlaylistEntity

@Dao
interface TrackInPlaylistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackInPlaylist(trackInPlaylistEntity: TrackInPlaylistEntity)
}