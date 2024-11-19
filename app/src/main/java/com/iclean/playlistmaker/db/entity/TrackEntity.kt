package com.iclean.playlistmaker.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackEntity (
    @PrimaryKey(autoGenerate = true)
    val keyId : Int,
    val trackId : Int,
    val artworkUrl100 : String,
    val trackName : String,
    val artistName : String,
    val collectionName : String,
    val releaseDate : String,
    val primaryGenreName : String,
    val country : String,
    val trackTimeMillis : String,
    val previewUrl : String,
    val isFavorite : Boolean
)