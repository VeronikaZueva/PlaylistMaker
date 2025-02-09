package com.iclean.playlistmaker.create.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.iclean.playlistmaker.create.domain.CreatePlaylistRepository
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class CreatePlaylistRepositoryImpl(
    private val appDataBase : AppDatabase,
    private val dbConvertor: PlaylistDbConvertor,
    private val context : Context
    )
    : CreatePlaylistRepository {

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
        val fileResult = file.toUri()

        return fileResult
    }

    //Сохраняем плейлист в базу
    override suspend fun insertPlaylist(playlist: Playlist) {
        appDataBase.playlistDao().insertPlaylist(dbConvertor.map(playlist))
    }


}