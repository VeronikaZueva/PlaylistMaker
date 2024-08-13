package com.iclean.playlistmaker.search.domain.impl

import com.iclean.playlistmaker.search.domain.SearchRepository
import com.iclean.playlistmaker.search.data.models.StateType
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import java.util.concurrent.Executors


class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    //Для асинхронной работы
    private val executor = Executors.newCachedThreadPool()
    override fun search(expression: String, consumer: SearchInteractor.Consumer) {
        //механизм поиска будем осуществляться через вызов метода поиска в дата слое
        //Для этого получим состояние ответа
        executor.execute{
            when(val stateType = repository.search(expression)) {
                is StateType.Success -> {consumer.consume(stateType.data, null)}
                else -> {consumer.consume(null, stateType.code)}
            }
        }
    }
    override fun load(): List<Track> {
        return repository.load()
    }

    override fun save(trackItem: Track) {
        repository.save(trackItem)
    }

    override fun clearHistory() {
        repository.clearHistory()
    }


}