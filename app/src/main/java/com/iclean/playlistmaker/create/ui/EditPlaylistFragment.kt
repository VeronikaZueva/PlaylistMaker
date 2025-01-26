package com.iclean.playlistmaker.create.ui


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.R.string.save_button
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.create.presentation.EditPlaylistViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlaylistFragment : CreatePlaylistFragment()  {
    override val viewModel by viewModel<EditPlaylistViewModel>()
    companion object {
        private const val PLAYLIST = "playlist"
        fun getArgs(key : Int) : Bundle = bundleOf(PLAYLIST to key)
    }

    //Определяем данные, которые проинициализируем позже
    private var uriOld = ""
    private var countOld = 0
    private var listOld = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlistId = requireArguments().getInt(PLAYLIST)

        binding.titleScreen.text = getString(R.string.edit_title)
        binding.createPlaylistButton.text = getString(save_button)

        viewModel.getPlaylistFromId(playlistId)
        viewModel.getLiveDataPlaylist().observe(viewLifecycleOwner) {
            super.playlistName = it.playlist.playlistName
            binding.playlistName.editText!!.setText(it.playlist.playlistName)
            binding.playlistDescription.editText!!.setText(it.playlist.playlistDescription)
            if(it.playlist.plailistImage != null) {
                Glide.with(this).load(it.playlist.plailistImage).transform(CenterCrop(), RoundedCorners(16))
                    .into(binding.posterAdd)
            }
            uriOld = it.playlist.plailistImage!!
            countOld = it.playlist.playlistCount
            listOld =it.playlist.playlistList!!
        }

        //Переопределяем возврат назад
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            findNavController().popBackStack()
            }
        })

        //Обновляем плейлист
        binding.createPlaylistButton.setOnClickListener {
            if(playlistName?.isNotEmpty() == true) {
                //Работаем с файлом
                if((playlistUri!=null) and (playlistUri!=uriOld.toUri())) {
                    lifecycleScope.launch {
                        playlistUri = viewModel.saveImage(playlistUri!!, playlistName!!)
                    }
                    lifecycleScope.launch {
                        viewModel.updatePlaylist(Playlist(
                            playlistId,
                            playlistName!!,
                            playlistDescription,
                            playlistUri.toString(),
                            listOld,
                            countOld)
                        )
                    }
                }
                else {
                    lifecycleScope.launch {
                        viewModel.updatePlaylist(
                            Playlist(
                            playlistId,
                            playlistName!!,
                            playlistDescription,
                            uriOld,
                            listOld,
                            countOld
                        ))
                    }
                }
                findNavController().popBackStack()

            }
        }


    }
}