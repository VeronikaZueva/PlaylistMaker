package com.iclean.playlistmaker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.iclean.playlistmaker.db.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist : PlaylistEntity)

    @Update(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlist_table ORDER BY playlistKey DESC")
    suspend fun getPlaylists() : List<PlaylistEntity>

    @Query("SELECT * FROM playlist_table WHERE playlistKey = :id")
    fun getPlaylistFromId(id : Int) : Flow<PlaylistEntity>

    @Query("DELETE FROM playlist_table WHERE playlistKey = :id ")
    suspend fun deletePlaylist(id : Int)

}