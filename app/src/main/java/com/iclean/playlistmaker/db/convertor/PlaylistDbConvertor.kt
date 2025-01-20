package com.iclean.playlistmaker.db.convertor

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.entity.PlaylistEntity


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

}