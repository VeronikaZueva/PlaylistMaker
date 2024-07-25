package com.iclean.playlistmaker.search.data.impl

import android.content.SharedPreferences
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.data.SearchRepository
import com.iclean.playlistmaker.search.data.dto.History
import com.iclean.playlistmaker.search.data.dto.Request
import com.iclean.playlistmaker.search.data.dto.Response
import com.iclean.playlistmaker.search.data.models.StateType
import com.iclean.playlistmaker.search.domain.models.Track


//Создаем реализацию интерфейса экрана поиска, который будет совершать действия, в зависимости от полученного ответа сервера
//Мы не знаем, какая библиотека используется, а потому, будем взаимодействовать с ней через класс NetWorkClient, который и будет возвращать нам ответы
//Так как NetworkClient берет на себя основную работу по взаимодействию с сервером, то создадим его объект в параметрах конструктора данного класса
//Также работаем в данном классе с историей
class SearchRepositoryImpl(sharedPreferences: SharedPreferences, private val networkClient: NetworkClient) : SearchRepository {

    private val history = History(sharedPreferences)
    override fun search(expression: String) : StateType<List<Track>> {
        //Создаем переменную, куда записываем полученный ответ сервера от заданного запроса
        val response = networkClient.search(Request(expression))
        return when(response.stateResponse) {
            -1 -> StateType.Error(-1)
            200 -> {
                StateType.Success((response as Response).result.map {
                    Track(it.trackId,
                        it.trackName,
                        it.artistName,
                        it.trackTimeMillis,
                        it.artworkUrl100,
                        it.collectionName,
                        it.releaseDate,
                        it.primaryGenreName,
                        it.country,
                        it.previewUrl)
                })
            }
            else -> StateType.Error(400)
        }

    }

    override fun load(): List<Track> {
        return history.load()
    }

    override fun save(trackItem: Track) {
        history.save(trackItem)
    }

    override fun clearHistory() {
        history.clearHistory()
    }

}