package com.iclean.playlistmaker.create.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.presentation.CreatePlaylistViewModel
import com.iclean.playlistmaker.databinding.FragmentCreatePlaylistBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

open class CreatePlaylistFragment : Fragment() {

    open lateinit var binding: FragmentCreatePlaylistBinding
    open val viewModel by viewModel<CreatePlaylistViewModel>()
    open lateinit var confirmDialog : MaterialAlertDialogBuilder

    //Выставляем изначальные поля пустыми
    open var playlistName : String? = null
    open var playlistDescription : String? = null
    open var playlistUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Открываем наш диалог
        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.end_create_playlist))
            .setMessage(getString(R.string.message_dialog))
            .setNeutralButton(getString(R.string.cancel_button)) {_, _ ->}
            .setNegativeButton(getString(R.string.finish_button)) { _, _ ->
                findNavController().popBackStack()
            }

        //Регистрируем событие, которое вызывает PhotoPicker и запускаем photopicker по нажатию
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            uri ->
            //Событие выбора фотографии пользователем
            if(uri != null) {
                Glide.with(this).load(uri).transform(CenterCrop(), RoundedCorners(16)).into(binding.posterAdd)
                playlistUri = uri
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
                binding.createPlaylistButton.setBackgroundColor(resources.getColor(R.color.blue))
            } else {
                //Меняем цвет кнопки
                @Suppress("DEPRECATION")
                binding.createPlaylistButton.setBackgroundColor(resources.getColor(R.color.gray))
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
            if((playlistUri != null) or (playlistName?.isNotEmpty() == true) or (playlistDescription?.isNotEmpty() == true )) {
                confirmDialog.show()
            } else {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if((playlistUri != null) or (playlistName?.isNotEmpty() == true) or (playlistDescription?.isNotEmpty() == true )) {
                    confirmDialog.show()
                } else {
                    findNavController().popBackStack()
                }
            }
        })


        //Кнопка "Создать" неактивна
        binding.createPlaylistButton.setOnClickListener {
            if(playlistName?.isNotEmpty() == true) {
                //Работаем с файлом
                if(playlistUri!=null) {
                    lifecycleScope.launch {
                        playlistUri = viewModel.saveImage(playlistUri!!, playlistName!!)
                    }
                    lifecycleScope.launch {
                        viewModel.insertPlaylist(playlistName!!, playlistDescription, playlistUri.toString())
                    }
                }
                else {
                    lifecycleScope.launch {
                        viewModel.insertPlaylist(
                            playlistName!!,
                            playlistDescription,
                            playlistUri.toString()
                        )
                    }
                }
                Toast.makeText(requireContext(), "Плейлист $playlistName создан", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()

            }
        }
    }


}