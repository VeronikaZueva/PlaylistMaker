package com.iclean.playlistmaker.main.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRootBinding
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
                R.id.playlist_item -> {
                    binding.bottomNavigationView.visibility = View.GONE
                } else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }




    }

}