package com.iclean.playlistmaker.db.convertor

import com.iclean.playlistmaker.db.entity.TrackEntity
import com.iclean.playlistmaker.search.domain.models.Track

class TrackDbConvertor {
    fun map(track : Track) : TrackEntity {
        return TrackEntity(
            0,
            track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.trackTimeMillis,
            track.previewUrl)
    }

    fun map(track : TrackEntity) : Track {
        return Track(
            track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.trackTimeMillis,
            track.previewUrl,
            true
        )
    }
}