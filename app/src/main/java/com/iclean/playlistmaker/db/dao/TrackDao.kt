package com.iclean.playlistmaker.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iclean.playlistmaker.db.entity.TrackEntity

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track : TrackEntity)

    @Delete(entity = TrackEntity::class)
    suspend fun deleteTrack(trackId : String)

    @Query("SELECT * FROM track_table ORDER BY keyId DESC")
    fun getTracks() : List<TrackEntity>

    @Query("SELECT keyId FROM track_table")
    fun getKeyId() : List<Int>
}