package com.iclean.playlistmaker.search.domain.models

//Отказываемся от Parcel, применимом в предыдущей версии приложения и переходим на @serialized в data слое
//Поэтому делаем стандартный класс-модель Track
data class Track (val trackId : String,
                  val trackName: String,
                  val artistName: String,
                  val trackTimeMillis: String,
                  val artworkUrl100: String,
                  val collectionName: String,
                  val releaseDate: String,
                  val primaryGenreName: String,
                  val country: String,
                  val previewUrl: String)