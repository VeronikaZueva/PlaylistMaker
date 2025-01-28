package com.iclean.playlistmaker.main.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.ui.CreatePlaylistFragment
import com.iclean.playlistmaker.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRootBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val destination = intent.getStringExtra("destination")
        if(destination == "to_create_playlist") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootFragmentContainerView, CreatePlaylistFragment())
                .commit()
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        //Скрыввем панель навигации для экранов Плейлист и Редактирование плейлиста
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.playlist_item, R.id.createPlaylist, R.id.editPlaylist -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    binding.divider.visibility = View.GONE
                } else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                }
            }
        }




    }

}