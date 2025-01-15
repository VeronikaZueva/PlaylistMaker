package com.iclean.playlistmaker.create.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.presentation.CreatePlaylistViewModel
import com.iclean.playlistmaker.databinding.ActivityCreatePlaylistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePlaylistActivity : AppCompatActivity() {

    private val viewModel by viewModel<CreatePlaylistViewModel>()
    private lateinit var binding : ActivityCreatePlaylistBinding

    //Выставляем изначальные поля пустыми
    private var playlistName : String? = null
    private var playlistDescription : String? = null


    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_playlist)

        binding = ActivityCreatePlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Возвращаемся домой
        binding.backButton.setOnClickListener {
            finish()
        }


        //Работаем с заполнением полей
        //Смотрим, изменилось ли состояние начального текста в поле
        binding.hintName.doOnTextChanged {
                text, _, _, _ ->
            //Если состояние изменилось, то присваиваем новый текст нашему Input Name и меняем цвет обводки
            playlistName = text?.toString()
            if(playlistName?.isNotEmpty() == true) {
                //Меняем цвет кнопки
                @Suppress("DEPRECATION")
                binding.createPlaylistButton.setBackgroundColor(getResources().getColor(R.color.blue))
            } else {
                //Меняем цвет кнопки
                @Suppress("DEPRECATION")
                binding.createPlaylistButton.setBackgroundColor(getResources().getColor(R.color.gray))
            }
        }

        //Смотрим, изменилось ли состояние начального текста в поле
        binding.hintDescription.doOnTextChanged {
                text, _, _, _ ->
            //Если состояние изменилось, то присваиваем новый текст нашему Input Name и меняем цвет обводки
            playlistDescription = text?.toString()

        }



        //Кнопка "Создать" неактивна
        binding.createPlaylistButton.setOnClickListener {
            if(playlistName?.isNotEmpty() == true) {
                Toast.makeText(this, "Плейлист создан", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Заполните поле Название", Toast.LENGTH_SHORT).show()

            }
        }
    }


}