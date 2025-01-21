package com.iclean.playlistmaker.db.convertor

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.entity.PlaylistEntity
import com.iclean.playlistmaker.db.entity.TrackInPlaylistEntity
import com.iclean.playlistmaker.search.domain.models.Track


class PlaylistDbConvertor {

        fun map(playlist : Playlist) : PlaylistEntity {
            return PlaylistEntity(
                playlistKey = playlist.id!!,
                playlistName = playlist.playlistName,
                playlistDescription = playlist.playlistDescription,
                plailistImage = playlist.plailistImage,
                playlistList = playlist.playlistList,
                playlistCount = playlist.playlistCount
            )
        }

    fun map(playlist : PlaylistEntity) : Playlist {
        return Playlist(
            id = playlist.playlistKey,
            playlistName = playlist.playlistName,
            playlistDescription = playlist.playlistDescription,
            plailistImage = playlist.plailistImage,
            playlistList = playlist.playlistList,
            playlistCount = playlist.playlistCount
        )
    }

    fun map(track : Track) : TrackInPlaylistEntity {
        return TrackInPlaylistEntity(0,
            trackId = track.trackId.toInt(),
            artworkUrl100 = track.artworkUrl100,
            trackName = track.trackName,
            artistName = track.artistName,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            trackTimeMillis = track.trackTimeMillis,
            previewUrl = track.previewUrl)
    }

}