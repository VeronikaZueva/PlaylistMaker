package com.iclean.playlistmaker.search.data.models

import com.google.gson.annotations.SerializedName

//Класс данных, определяющий формат сущности Трека в конечном виде

data class TrackDto (
    @SerializedName("trackName") val trackName : String,
    @SerializedName("artistName") val artistName : String,
    @SerializedName("trackTimeMillis") val trackTimeMillis : String,
    @SerializedName("artworkUrl100") val artworkUrl100 : String,
    @SerializedName("trackId") val trackId : String,
    @SerializedName("collectionName") val collectionName : String,
    @SerializedName("releaseDate") val releaseDate : String?,
    @SerializedName("primaryGenreName") val primaryGenreName : String,
    @SerializedName("country") val country : String,
    @SerializedName("previewUrl") val previewUrl : String?
)