package com.iclean.playlistmaker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iclean.playlistmaker.db.dao.TrackDao
import com.iclean.playlistmaker.db.entity.TrackEntity

@Database(version = 3, entities = [TrackEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao() : TrackDao
}