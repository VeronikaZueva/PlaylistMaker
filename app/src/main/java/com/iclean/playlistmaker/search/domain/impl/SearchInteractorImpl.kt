package com.iclean.playlistmaker.search.domain.impl


import com.iclean.playlistmaker.search.domain.SearchRepository
import com.iclean.playlistmaker.search.data.models.StateType
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {

    override fun search(expression: String) : Flow<Pair<List<Track>?, Int?>> {
        return repository.search(expression).map { result ->
            when(result) {
                is StateType.Success -> {
                    Pair(result.data, null)
                }
                is StateType.Error -> {
                    Pair(null, result.code)
                }
            }
        }

    }



}