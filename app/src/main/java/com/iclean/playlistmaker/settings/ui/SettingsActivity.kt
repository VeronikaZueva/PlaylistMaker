package com.iclean.playlistmaker.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.ActivitySettingsBinding
import com.iclean.playlistmaker.settings.presentation.SettingsViewModel


class SettingsActivity : AppCompatActivity() {

    //Создаем нашу ViewModel и Binding
    private lateinit var viewModel : SettingsViewModel
    private lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, SettingsViewModel.getViewModelFactory(this))[SettingsViewModel::class.java]

        //Возвращаемся домой
        binding.backButton.setOnClickListener {
            this.finish()
        }

        //Переключаем тему
        binding.themeSwitcher.apply {
            viewModel.switchThemeLiveData().observe(this@SettingsActivity) {
                isChecked = it.darkTheme
                setOnCheckedChangeListener  {_, _ -> viewModel.switchTheme(isChecked)}
            }
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