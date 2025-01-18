package com.iclean.playlistmaker.create.domain.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.iclean.playlistmaker.create.domain.CreatePlaylistInteractor
import com.iclean.playlistmaker.create.domain.CreatePlaylistRepository
import com.iclean.playlistmaker.create.domain.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class CreatePlaylistInteractorImpl(
    private val createPlaylistRepository: CreatePlaylistRepository,
    private val context : Context)
    : CreatePlaylistInteractor {

        //Сохраняем изображение в хранилище
    override suspend fun saveImage(uri: Uri, nameFile: String) : Uri {
            //Определяем путь сохранения - специф.внешнее хранилище приложения
            val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), nameFile)
            if(!filePath.exists()) {filePath.mkdirs()}
            //Задаем имя файлу
            val fileName = "$nameFile.jpg"
            //Преобразуем файл в формат JPEG, предварительно сохдав объект файла
            val file = File(filePath, fileName)
            val inputStream = context.contentResolver.openInputStream(uri)
            val outStream = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }
            BitmapFactory.decodeStream(inputStream).compress(Bitmap.CompressFormat.JPEG, 30, outStream)
        return file.toUri()
    }

    //Сохраняем плейлист в БД
    override suspend fun insertPlaylist(playlist : Playlist) {
        createPlaylistRepository.insertPlaylist(playlist)
    }

    }