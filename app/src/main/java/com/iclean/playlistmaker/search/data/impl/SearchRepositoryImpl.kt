package com.iclean.playlistmaker.search.data.impl

import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.domain.SearchRepository
import com.iclean.playlistmaker.search.data.dto.Request
import com.iclean.playlistmaker.search.data.dto.Response
import com.iclean.playlistmaker.search.data.models.StateType
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


//Создаем реализацию интерфейса экрана поиска, который будет совершать действия, в зависимости от полученного ответа сервера
//Мы не знаем, какая библиотека используется, а потому, будем взаимодействовать с ней через класс NetWorkClient, который и будет возвращать нам ответы
//Так как NetworkClient берет на себя основную работу по взаимодействию с сервером, то создадим его объект в параметрах конструктора данного класса
//Также работаем в данном классе с историей
class SearchRepositoryImpl(private val networkClient: NetworkClient, private val db : AppDatabase) :
    SearchRepository {

    override fun search(expression: String) : Flow<StateType<List<Track>>> = flow {
        //Получаем список id избранных треков
        val faviriteIdList = db.trackDao().getTrackIdForFavorite()
        //Создаем переменную, куда записываем полученный ответ сервера от заданного запроса
        val response = networkClient.search(Request(expression))
        when(response.stateResponse) {
            -1 -> emit(StateType.Error(-1))
            200 -> {
                with(response as Response) {
                    val data = results.map {
                        Track(it.trackId,
                            it.trackName,
                            it.artistName,
                            it.trackTimeMillis,
                            it.artworkUrl100,
                            it.collectionName,
                            it.releaseDate,
                            it.primaryGenreName,
                            it.country,
                            it.previewUrl,
                            it.trackId.toInt() in faviriteIdList
                        )
                    }

                    emit(StateType.Success(data))
                }

            }
            else -> emit(StateType.Error(400))
        }

    }

}