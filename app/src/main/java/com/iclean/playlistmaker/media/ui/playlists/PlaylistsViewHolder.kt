package com.iclean.playlistmaker.media.ui.playlists

import androidx.recyclerview.widget.RecyclerView
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.PlaylistViewBinding

class PlaylistsViewHolder(private val binding: PlaylistViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(model : Playlist) = with(binding){
            playlistName.text = model.playlistName
    }

}