package com.iclean.playlistmaker.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iclean.playlistmaker.App
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.ActivitySettingsBinding
import com.iclean.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {

    //Создаем нашу ViewModel и Binding
    private val viewModel by viewModel<SettingsViewModel>()
    private lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //Возвращаемся домой
        binding.backButton.setOnClickListener {
            this.finish()
        }

        //Переключаем тему
        binding.themeSwitcher.isChecked = (application as App).darkTheme
        binding.themeSwitcher.setOnCheckedChangeListener  {_, isChecked ->
            (application as App).switchTheme(isChecked)
            viewModel.switchTheme(isChecked)
        }


        //Поделиться приложением
        binding.shareButton.setOnClickListener {
            viewModel.shareApp()
        }

        //Написать в поддержку
        binding.sendButton.setOnClickListener {
            viewModel.sendMessage()
        }

        //Документация
        binding.userDocs.setOnClickListener {
            viewModel.goToLink()
        }

    }
}