package com.iclean.playlistmaker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iclean.playlistmaker.db.dao.PlaylistDao
import com.iclean.playlistmaker.db.dao.TrackDao
import com.iclean.playlistmaker.db.entity.PlaylistEntity
import com.iclean.playlistmaker.db.entity.TrackEntity

@Database(version = 5 , entities = [TrackEntity::class, PlaylistEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao() : TrackDao
    abstract fun playlistDao() : PlaylistDao
}