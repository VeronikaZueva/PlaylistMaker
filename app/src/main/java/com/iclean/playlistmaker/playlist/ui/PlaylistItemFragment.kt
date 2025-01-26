package com.iclean.playlistmaker.playlist.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.FragmentPlaylistItemBinding
import com.iclean.playlistmaker.general.PlaylistMethods
import com.iclean.playlistmaker.player.ui.PlayerActivity
import com.iclean.playlistmaker.playlist.presentation.LiveDataForPlaylist
import com.iclean.playlistmaker.playlist.presentation.PlaylistItemViewModel
import com.iclean.playlistmaker.playlist.domain.api.TrackClick
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch
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
    private lateinit var adapter: PlaylistItemAdapter
    private lateinit var confirmDialog : MaterialAlertDialogBuilder
    private lateinit var confirmDialogMenu : MaterialAlertDialogBuilder


    lateinit var playlistName : String
    private var playlistDescription : String? = null
    var playlistCount : Int = 0
    private var playlistImage : String? = null
    var tracklistAfterRemove : String? = null
    var trackForDelete : Int = 0
    private lateinit var myTrackList : List<Track>



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
        val playlistId= requireArguments().getInt(PLAYLIST)
        val tracks = requireArguments().getString(TRACKLIST)
        val trackList = convertFormat(tracks)

        viewModel.getPlaylistFromId(playlistId)
        viewModel.getLiveDataPlaylist().observe(viewLifecycleOwner) {
            state -> renderPlaylist(state)
        }

        //Задаем BottomSheet и отображаем в нем треки
        val bottomSheetContainer = binding.bottomSheet
        val bottomSheetBehaivor = BottomSheetBehavior.from(bottomSheetContainer)
        bottomSheetBehaivor.state = BottomSheetBehavior.STATE_COLLAPSED

        val trackClick = object : TrackClick {
            override fun getTrack(track: Track) {
                val intent = Intent(requireContext(), PlayerActivity::class.java)
                intent.putExtra("trackObject", Gson().toJson(track))
                startActivity(intent)
            }
            override fun removeTrack(track: Int) {
                val mutableListTrack : MutableList<Int> = trackList.toMutableList()
                Log.i("Список до удаления", "$mutableListTrack")
                mutableListTrack.remove(track)
                tracklistAfterRemove = mutableListTrack.joinToString()
                trackForDelete = track
                confirmDialog.show()
            }
        }

        adapter = PlaylistItemAdapter(trackClick)
        adapter.submitList(listOf())


        viewModel.getTracksForPlaylist(trackList)
        viewModel.getLiveDataTracklist().observe(viewLifecycleOwner) {
            if(it.status !=1) {
                myTrackList = it.tracklist
                adapter.submitList(it.tracklist)
                val time = playlistMethods.dateFormatTrack(it.time)
                binding.playlistDuration.text = time

            } else {
                adapter.submitList(listOf())
            }
            binding.playlists.adapter = adapter
            binding.playlists.layoutManager = LinearLayoutManager(requireContext())
        }

        //Открываем наш диалог с подтверждением удаления
        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.remove_track))
            .setNeutralButton(getString(R.string.cancel_button)) {_, _ ->}
            .setNegativeButton(getString(R.string.delete)) { _, _ ->
                deleteTrack(playlistId)
            }

        //Отображаем меню
        val bottomSheetMenu = binding.bottomMenu
        val bottomSheetBehaivorMenu = BottomSheetBehavior.from(bottomSheetMenu)
        bottomSheetBehaivorMenu.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehaivorMenu.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> binding.overlay.visibility = View.GONE
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                    else -> binding.overlay.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.overlay.alpha = slideOffset
            }
        })

        confirmDialogMenu = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.want_delete_playlist))
            .setNeutralButton(getString(R.string.no)) {_, _ ->}
            .setNegativeButton(getString(R.string.yes)) { _, _ ->
                deletePlaylist(playlistId)
            }

        //Обработчики кнопок
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.shareButton.setOnClickListener {
            if(playlistCount > 0) {
                viewModel.share(playlistName, playlistDescription, playlistCount, myTrackList)
            } else {
                Toast.makeText(requireContext(), R.string.no_share, Toast.LENGTH_SHORT).show()
            }
        }
        binding.menuButton.setOnClickListener {
            bottomSheetBehaivorMenu.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.shareBottomMenu.setOnClickListener {
            if(playlistCount > 0) {
                viewModel.share(playlistName, playlistDescription, playlistCount, myTrackList)
            } else {
                Toast.makeText(requireContext(), R.string.no_share, Toast.LENGTH_SHORT).show()
            }
        }
        binding.deletePlaylist.setOnClickListener {
            confirmDialogMenu.show()
        }
        binding.editPlaylist.setOnClickListener {
            //Редактирование плейлиста
        }
    }

    //Отображаем контент
    @SuppressLint("SetTextI18n")
    private fun renderPlaylist(playlist : LiveDataForPlaylist)  {
        showDataPlaylist(playlist.playlist)
    }



    private fun showDataPlaylist(playlist : Playlist) {
        playlistName = playlist.playlistName
        playlistDescription = playlist.playlistDescription
        playlistCount = playlist.playlistCount
        playlistImage = playlist.plailistImage
        val placeholder = R.drawable.placeholder

        binding.playlistName.text = playlistName
        binding.playlistDescription.text = playlistDescription
        binding.playlistCount.text = playlistMethods.setCountTrack(playlistCount)
        playlistMethods.setImage(requireContext(), playlistImage, binding.poster, placeholder, 8)
        binding.playlistNameBottom.text = playlistName
        binding.countTrack.text = playlistMethods.setCountTrack(playlistCount)
        playlistMethods.setImage(requireContext(), playlistImage, binding.posterBottom, placeholder, 8)
    }

    private fun convertFormat(tracks : String?) : List<Int> {
        return tracks?.split(", ")?.mapNotNull { it.toIntOrNull() } ?: listOf()
    }

    private fun deleteTrack(playlistId : Int) {
        Log.i("Строка в диалоге", "$tracklistAfterRemove")
        //1. Обновить список треков у плейлиста
        viewModel.updatePlaylist(
            Playlist(
                playlistId,
                playlistName,
                playlistDescription,
                playlistImage,
                tracklistAfterRemove,
                playlistCount-1
            ), convertFormat(tracklistAfterRemove)
        )

        //2. Проверить, есть ли данный трек хотя бы в одном плейлисте
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.checkTrackAllPlaylists(trackForDelete)
        }
    }

    private fun deletePlaylist(playlistId: Int) {
            viewModel.deletePlaylist(playlistId)
            findNavController().popBackStack()
    }

}