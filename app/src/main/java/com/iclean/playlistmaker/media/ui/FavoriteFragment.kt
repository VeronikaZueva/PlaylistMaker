package com.iclean.playlistmaker.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iclean.playlistmaker.databinding.FavoriteFragmentBinding

class FavoriteFragment : Fragment() {
    companion object {
        fun newInstance() = FavoriteFragment().apply{}
    }
    private lateinit var binding : FavoriteFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container:ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}