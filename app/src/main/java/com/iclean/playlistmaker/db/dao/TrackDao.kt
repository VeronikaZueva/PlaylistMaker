package com.iclean.playlistmaker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iclean.playlistmaker.db.entity.TrackEntity

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track : TrackEntity)

    @Query("DELETE FROM track_table WHERE trackId = :id ")
    suspend fun deleteTrack(id : Int)

    @Query("SELECT * FROM track_table ORDER BY trackKey DESC")
    fun getTracks() : List<TrackEntity>

    @Query("SELECT trackId FROM track_table")
    fun getTrackIdForFavorite() : List<Int>
}