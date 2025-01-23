package com.iclean.playlistmaker.media.ui.playlists

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.PlaylistFragmentBinding
import com.iclean.playlistmaker.media.domain.api.ClickPlaylistItem
import com.iclean.playlistmaker.media.presentation.playlists.PlaylistFragmentViewModel
import com.iclean.playlistmaker.playlist.ui.PlaylistItemFragment

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistFragment().apply {}
        const val DELAY = 1000L
    }

    private lateinit var binding : PlaylistFragmentBinding
    private val viewModel by viewModel<PlaylistFragmentViewModel>()
    private lateinit var adapter : PlaylistsAdapter
    private var isClickAllowed = true
    private lateinit  var contentView : View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        if (!::contentView.isInitialized) {
            binding = PlaylistFragmentBinding.inflate(inflater, container, false)
            return binding.root
        }
        return contentView
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Получаем плейлисты
        val playlistItemClick = object : ClickPlaylistItem {
            override fun goToPlaylist(playlist: Playlist) {
                if(clickDebounce()) {
                    findNavController().navigate(R.id.to_playlist_item, PlaylistItemFragment.getArguments(playlist.id))
                }
            }
        }
        adapter = PlaylistsAdapter(playlistItemClick)
        adapter.submitList(listOf())

        viewModel.returnPlaylists()

        viewModel.getLiveData().observe(this as LifecycleOwner) {
            if(it.status != 1) {
                adapter.submitList(it.playlists)
                renderScreen(false)
            } else {
                adapter.submitList(listOf())
                renderScreen(true)
            }
            binding.playlists.adapter = adapter
            binding.playlists.layoutManager = GridLayoutManager(requireActivity(), 2)

        }


        //Переход на новый плейлист
        binding.newButton.setOnClickListener {
            findNavController().navigate(R.id.to_create_playlist)

        }
    }

        //Видимость слоев
        private fun renderScreen(isEmpty : Boolean) {
            if(isEmpty) {
                binding.statusImage.visibility = View.VISIBLE
                binding.favouriteText.visibility = View.VISIBLE
                binding.playlists.visibility = View.GONE
            } else {
                binding.statusImage.visibility = View.GONE
                binding.favouriteText.visibility = View.GONE
                binding.playlists.visibility = View.VISIBLE


            }
        }

    override fun onResume() {
        super.onResume()
        viewModel.returnPlaylists()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(DELAY)
                isClickAllowed = true
            }

        }
        return current
    }
}