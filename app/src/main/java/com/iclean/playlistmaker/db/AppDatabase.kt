package com.iclean.playlistmaker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iclean.playlistmaker.db.dao.PlaylistDao
import com.iclean.playlistmaker.db.dao.TrackDao
import com.iclean.playlistmaker.db.dao.TrackInPlaylistDao
import com.iclean.playlistmaker.db.entity.PlaylistEntity
import com.iclean.playlistmaker.db.entity.TrackEntity
import com.iclean.playlistmaker.db.entity.TrackInPlaylistEntity

@Database(version = 8 , entities = [TrackEntity::class, PlaylistEntity::class, TrackInPlaylistEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao() : TrackDao
    abstract fun playlistDao() : PlaylistDao
    abstract fun trackInPlaylistDao() : TrackInPlaylistDao
}