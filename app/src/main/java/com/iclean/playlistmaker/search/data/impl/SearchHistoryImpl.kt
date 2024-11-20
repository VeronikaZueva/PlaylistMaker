package com.iclean.playlistmaker.search.data.impl


import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInteractor
import com.iclean.playlistmaker.search.domain.models.Track


class SearchHistoryImpl(private val history : SearchHistoryInteractor,
                        private val db: AppDatabase   ) : HistoryInt  {

    override fun load() : List<Track> {
        return history.load()
    }
    override suspend fun save(trackItem : Track) {

            val faviriteIdList = db.trackDao().getTrackIdForFavorite()
            faviriteIdList.filter { trackItem.trackId.toInt() in faviriteIdList }
                .map { trackItem.isFavorite = true }

        return history.save(trackItem)
    }

    override fun clearHistory() {
        return history.clearHistory()
    }


}