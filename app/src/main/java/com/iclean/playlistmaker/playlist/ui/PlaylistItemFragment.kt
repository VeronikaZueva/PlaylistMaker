package com.iclean.playlistmaker.playlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.FragmentPlaylistItemBinding
import com.iclean.playlistmaker.playlist.presentation.PlaylistItemState
import com.iclean.playlistmaker.playlist.presentation.PlaylistItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistItemFragment : Fragment() {

    companion object {
        private const val PLAYLIST = "playlist"
        fun getArguments(key: Int?) : Bundle = bundleOf(PLAYLIST to key)
    }

    private lateinit var binding : FragmentPlaylistItemBinding
    private val viewModel by viewModel<PlaylistItemViewModel>()

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
        viewModel.getPlaylistFromId(playlistId)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            state -> renderPlaylist(state)
        }




        //Обработчики кнопок
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    //Отображаем контент
    private fun renderPlaylist(state : PlaylistItemState) {
        when (state) {
            is PlaylistItemState.PlaylistItemLiveData -> showContent(state.playlist)
            PlaylistItemState.EmptyState -> Unit
        }
    }

    private fun showContent(playlist : Playlist) {
        val playlistName = playlist.playlistName
        val playlistDescription = playlist.playlistDescription
        val playlistCount = playlist.playlistCount
        val playlistList = playlist.playlistList
        val playlistImage = playlist.plailistImage

        binding.playlistName.text = playlistName
        binding.playlistDescription.text = playlistDescription


    }




}