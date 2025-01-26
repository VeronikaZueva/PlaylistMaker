package com.iclean.playlistmaker.create.ui

import com.iclean.playlistmaker.create.presentation.EditPlaylistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlaylistFragment : CreatePlaylistFragment()  {
    override val viewModel by viewModel<EditPlaylistViewModel>()
}