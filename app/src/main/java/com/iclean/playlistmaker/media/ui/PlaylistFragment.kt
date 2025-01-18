package com.iclean.playlistmaker.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.PlaylistFragmentBinding
import com.iclean.playlistmaker.media.ui.playlists.PlaylistsAdapter

class PlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistFragment().apply {}
    }

        private lateinit var binding : PlaylistFragmentBinding

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
            binding = PlaylistFragmentBinding.inflate(inflater, container, false)
            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Отображаем список плейлистов
        val adapter = PlaylistsAdapter()
        binding.playlists.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.playlists.adapter = adapter


        //Переход на новый плейлист
        binding.newButton.setOnClickListener {
            findNavController().navigate(R.id.to_create_playlist)
        }
    }


}