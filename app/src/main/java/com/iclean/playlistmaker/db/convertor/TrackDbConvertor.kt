package com.iclean.playlistmaker.db.convertor

import com.iclean.playlistmaker.db.entity.TrackEntity
import com.iclean.playlistmaker.search.domain.models.Track

class TrackDbConvertor {
    fun map(track : Track) : TrackEntity {
        return TrackEntity(0,
            trackId = track.trackId.toInt(),
            artworkUrl100 = track.artworkUrl100,
            trackName = track.trackName,
            artistName = track.artistName,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            trackTimeMillis = track.trackTimeMillis,
            previewUrl = track.previewUrl,
            isFavorite = track.isFavorite)
    }

    fun map(track : TrackEntity) : Track {
        return Track(
            trackId = track.trackId.toString(),
            artworkUrl100 = track.artworkUrl100,
            trackName = track.trackName,
            artistName = track.artistName,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            trackTimeMillis = track.trackTimeMillis,
            previewUrl = track.previewUrl,
            isFavorite = true
        )
    }
}