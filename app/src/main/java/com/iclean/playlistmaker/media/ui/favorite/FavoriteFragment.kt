package com.iclean.playlistmaker.media.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.FavoriteFragmentBinding
import com.iclean.playlistmaker.main.ui.StorageTrack
import com.iclean.playlistmaker.media.presentation.favorite.FavoriteFragmentViewModel
import com.iclean.playlistmaker.search.domain.api.TrackClick
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.search.ui.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    companion object {
        fun newInstance() = FavoriteFragment().apply{}
    }
    private lateinit var binding : FavoriteFragmentBinding
    private val viewModel by viewModel<FavoriteFragmentViewModel>()

    //Необходимые переменные для списка треков
    private lateinit var trackAdapter: TrackAdapter

    //Методы жизненного цикла Fragment
    override fun onCreateView(inflater: LayoutInflater, container:ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Объект списка
        val trackClick = object : TrackClick {
            override fun getTrack(track: Track) {
                    //Переходим к нему через Intent
                    (requireActivity() as StorageTrack).setCurrentTrack(Gson().toJson(track))
                    findNavController().navigate(R.id.to_mediaPlayer)
                }
        }

        //Задаем сам список
        trackAdapter = TrackAdapter(trackClick)
        trackAdapter.submitList(listOf())


        //Получаем треки
        viewModel.returnFavoriteTracks()

        viewModel.getLiveData().observe(this as LifecycleOwner) {
            if(it.error != 1) {
                trackAdapter.submitList(it.tracks)
                renderScreen(false)
            }
            else {
                trackAdapter.submitList(listOf())
                renderScreen(true)
            }
            binding.reciclerViewTrack.adapter = trackAdapter
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.returnFavoriteTracks()
    }

//Функции внутри фрагмента

    private fun renderScreen(isEmpty : Boolean) {
        if(isEmpty) {
            binding.reciclerViewTrack.visibility = View.GONE
            binding.statusImage.visibility = View.VISIBLE
            binding.favouriteText.visibility = View.VISIBLE
        } else {
            binding.reciclerViewTrack.visibility = View.VISIBLE
            binding.statusImage.visibility = View.GONE
            binding.favouriteText.visibility = View.GONE
        }
    }



}