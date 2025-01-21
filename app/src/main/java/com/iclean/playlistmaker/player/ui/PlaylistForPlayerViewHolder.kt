package com.iclean.playlistmaker.player.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.PlaylistBottomViewBinding

class PlaylistForPlayerViewHolder(private val binding: PlaylistBottomViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(model : Playlist) = with(binding){
            playlistName.text = model.playlistName
            countTrack.text = countTracks(model.playlistCount)
            Glide.with(itemView).load(model.plailistImage)
                .transform(CenterCrop(), RoundedCorners(8))
                .placeholder(R.drawable.placeholder).into(poster)
        }

        private fun countTracks(countTrack : Int) : String {
            return if(countTrack % 10 == 1) {
                "$countTrack трек"
            } else {
                if(countTrack % 10 == 2 or 3 or 4) {
                    "$countTrack трека"
                } else if(countTrack == 11 or  12 or 13) {
                    "$countTrack треков"
                } else {
                    "$countTrack треков"
                }
            }
        }

    }
