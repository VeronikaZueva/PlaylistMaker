package com.iclean.playlistmaker.playlist.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.FragmentPlaylistItemBinding
import com.iclean.playlistmaker.general.PlaylistMethods
import com.iclean.playlistmaker.player.ui.PlayerActivity
import com.iclean.playlistmaker.playlist.presentation.LiveDataForPlaylist
import com.iclean.playlistmaker.playlist.presentation.PlaylistItemViewModel
import com.iclean.playlistmaker.search.domain.api.TrackClick
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.search.ui.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistItemFragment : Fragment() {

    companion object {
        private const val PLAYLIST = "playlist"
        private const val TRACKLIST = "tracklist"
        fun getArguments(key: Int?, list : String?) : Bundle = bundleOf(PLAYLIST to key, TRACKLIST to list)
    }

    private lateinit var binding : FragmentPlaylistItemBinding
    private val viewModel by viewModel<PlaylistItemViewModel>()
    private val playlistMethods = PlaylistMethods()
    private lateinit var adapter: TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Получаем переданные данные и устанавливаем поля
        val playlistId = requireArguments().getInt(PLAYLIST)
        val tracks = requireArguments().getString(TRACKLIST)
        val trackList = tracks?.split(", ")?.mapNotNull { it.toIntOrNull() } ?: listOf()


        viewModel.getPlaylistFromId(playlistId)
        viewModel.getLiveDataPlaylist().observe(viewLifecycleOwner) {
            state -> renderPlaylist(state)
        }


        //Задаем BottomSheet и отображаем в нем треки
        val bottomSheetContainer = binding.bottomSheet
        val bottomSheetBehaivor = BottomSheetBehavior.from(bottomSheetContainer)
        bottomSheetBehaivor.state = BottomSheetBehavior.STATE_EXPANDED

        val trackClick = object : TrackClick {
            override fun getTrack(track: Track) {
                val intent = Intent(requireContext(), PlayerActivity::class.java)
                intent.putExtra("trackObject", Gson().toJson(track))
                startActivity(intent)
            }
        }

        adapter = TrackAdapter(trackClick)
        adapter.submitList(listOf())


        viewModel.getTracksForPlaylist(trackList)
        viewModel.getLiveDataTracklist().observe(viewLifecycleOwner) {
            if(it.status !=1) {
                adapter.submitList(it.tracklist)
            } else {
                adapter.submitList(listOf())
            }
            binding.playlists.adapter = adapter
            binding.playlists.layoutManager = LinearLayoutManager(requireContext())
        }

        //Обработчики кнопок
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    //Отображаем контент
    @SuppressLint("SetTextI18n")
    private fun renderPlaylist(playlist : LiveDataForPlaylist)  {
        showDataPlaylist(playlist.playlist)
    }



    private fun showDataPlaylist(playlist : Playlist) {
        val playlistName = playlist.playlistName
        val playlistDescription = playlist.playlistDescription
        val playlistCount = playlist.playlistCount
        val playlistImage = playlist.plailistImage
        val placeholder = R.drawable.placeholder

        binding.playlistName.text = playlistName
        binding.playlistDescription.text = playlistDescription
        binding.playlistCount.text = playlistMethods.setCountTrack(playlistCount)
        playlistMethods.setImage(requireContext(), playlistImage, binding.poster, placeholder, 8)
    }



}