package com.iclean.playlistmaker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iclean.playlistmaker.databinding.FragmentSettingsBinding
import com.iclean.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    //Создаем нашу ViewModel и Binding
    private val viewModel by viewModel<SettingsViewModel>()
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Переключаем тему - переписываем с использованием LiveData
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            binding.themeSwitcher.isChecked = it.darkTheme
        }

        binding.themeSwitcher.setOnCheckedChangeListener {_, isChecked ->
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