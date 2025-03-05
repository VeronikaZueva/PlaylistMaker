package com.iclean.playlistmaker.media.ui.playlists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.PlaylistViewBinding
import com.iclean.playlistmaker.media.domain.api.ClickPlaylistItem

class PlaylistsAdapter(private val playlistItemClick : ClickPlaylistItem) : ListAdapter<Playlist, PlaylistsViewHolder>(PlaylistComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        val view = LayoutInflater.from(parent.context)
        return PlaylistsViewHolder(PlaylistViewBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val playlistItem = getItem(position)
        holder.bind(playlistItem)
        holder.itemView.setOnClickListener {
            playlistItemClick.goToPlaylist(playlistItem)
        }
    }

}