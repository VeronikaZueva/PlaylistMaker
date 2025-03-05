package com.iclean.playlistmaker.player.ui

import androidx.recyclerview.widget.RecyclerView
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.PlaylistBottomViewBinding
import com.iclean.playlistmaker.general.PlaylistMethods

class PlaylistForPlayerViewHolder(private val binding: PlaylistBottomViewBinding): RecyclerView.ViewHolder(binding.root) {
    private val playlistMethods = PlaylistMethods()

        fun bind(model : Playlist) = with(binding){
            playlistName.text = model.playlistName
            countTrack.text = countTracks(model.playlistCount)
            val context = itemView.context
            val trackImage = model.plailistImage
            val trackUrl = binding.poster
            val placeholder = R.drawable.placeholder

            playlistMethods.setImage(context, trackImage, trackUrl, placeholder, 8)
        }

        private fun countTracks(countTrack : Int) : String {
            return playlistMethods.setCountTrack(countTrack)
        }

    }
