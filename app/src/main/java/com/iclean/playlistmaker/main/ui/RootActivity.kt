package com.iclean.playlistmaker.main.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity(), StorageTrack {
    private lateinit var binding : ActivityRootBinding
    private var trackBundle : String = ""
    override fun getCurrentTrack() : String {
        return trackBundle
    }
    override fun setCurrentTrack(track : String) {
        trackBundle = track
    }
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        //Скрыввем панель навигации для экранов Плейлист и Редактирование плейлиста
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.playlist_item, R.id.createPlaylist, R.id.editPlaylist, R.id.mediaPlayer -> {
                    binding.bottomNavigationView.visibility = View.GONE

                } else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE

                }
            }
        }




    }

}