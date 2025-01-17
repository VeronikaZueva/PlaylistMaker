package com.iclean.playlistmaker.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistKey : Int,
    val playlistName : String,
    val playlistDescription : String?,
    val plailistImage : String?,
    val playlistList: String?,
    val playlistCount : Int
)
