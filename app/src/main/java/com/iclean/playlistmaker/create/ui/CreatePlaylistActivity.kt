package com.iclean.playlistmaker.create.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.presentation.CreatePlaylistViewModel
import com.iclean.playlistmaker.databinding.ActivityCreatePlaylistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePlaylistActivity : AppCompatActivity() {

    private val viewModel by viewModel<CreatePlaylistViewModel>()
    private lateinit var binding : ActivityCreatePlaylistBinding
    private lateinit var confirmDialog : MaterialAlertDialogBuilder

    //Выставляем изначальные поля пустыми
    private var playlistName : String? = null
    private var playlistDescription : String? = null
    private var isImage = false


    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_playlist)

        binding = ActivityCreatePlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Открываем наш диалог
        confirmDialog = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.end_create_playlist))
            .setMessage(getString(R.string.message_dialog))
            .setNeutralButton(getString(R.string.cancel_button)) {_, _ ->}
            .setNegativeButton(getString(R.string.finish_button)) { _, _ ->
                finish()
            }

        //Регистрируем событие, которое вызывает PhotoPicker и запускаем photopicker по нажатию
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            uri ->
            //Событие выбора фотографии пользователем
            if(uri != null) {
                Glide.with(this).load(uri).transform(CenterCrop(), RoundedCorners(16)).into(binding.posterAdd)
                isImage = true
            }
        }
        binding.posterAdd.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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

        //Смотрим, изменилось ли состояние начального текста в поле Описание
        binding.hintDescription.doOnTextChanged {
                text, _, _, _ ->
            //Если состояние изменилось, то присваиваем новый текст нашему Input Name и меняем цвет обводки
            playlistDescription = text?.toString()

        }

        //Показываем диалог, если случилось состояние возврата (системное или по кнопке)
        binding.backButton.setOnClickListener {
            if(isImage or (playlistName?.isNotEmpty() == true) or (playlistDescription?.isNotEmpty() == true )) {
                confirmDialog.show()
            } else {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(isImage or (playlistName?.isNotEmpty() == true) or (playlistDescription?.isNotEmpty() == true )) {
                    confirmDialog.show()
                } else {
                    finish()
                }
            }
        })


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