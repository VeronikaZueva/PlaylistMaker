package com.iclean.playlistmaker.player.presentation.presents

import com.iclean.playlistmaker.TrackResponse


class PlayerActivityPresents  {

    companion object {
        const val DELAY = 300L
        const val TIMER = "00:00"
    }

    fun getTrackName(trackItem : TrackResponse.Track?) : String {
        return trackItem?.trackName.toString()
    }
    fun getArtistName(trackItem : TrackResponse.Track?) : String {
        return trackItem?.artistName.toString()
    }
    fun getTrackTimeMillis(trackItem : TrackResponse.Track?) : String {
        return trackItem?.trackTimeMillis.toString()
    }
    fun getArtworkUrl100(trackItem : TrackResponse.Track?) : String {
        return trackItem?.artworkUrl100.toString()
    }
    fun getCollectionName(trackItem : TrackResponse.Track?) : String {
        return trackItem?.collectionName.toString()
    }
    private fun getReleaseDate(trackItem : TrackResponse.Track?) : String {
        return trackItem?.releaseDate.toString()
    }
    fun getPrimaryGenreName(trackItem : TrackResponse.Track?) : String {
        return trackItem?.primaryGenreName.toString()
    }
    fun getCountry(trackItem : TrackResponse.Track?) : String {
        return trackItem?.country.toString()
    }

    fun getPreviewUrl(trackItem: TrackResponse.Track?) : String {
        return trackItem?.previewUrl.toString()
    }


    //Преобразуем ссылку на изображение в полный формат
    fun getBigPoster(trackItem : TrackResponse.Track?) : String {
        return getArtworkUrl100(trackItem).replaceAfterLast('/', "512x512bb.jpg")
    }
     fun getYearFormat(trackItem : TrackResponse.Track?) : String {
        return getReleaseDate(trackItem).substring(0, 4)
    }






}