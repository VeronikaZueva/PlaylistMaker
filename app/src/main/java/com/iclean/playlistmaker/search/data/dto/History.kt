package com.iclean.playlistmaker.search.data.dto

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.models.Track



class History(private val sharePref: SharedPreferences) : HistoryInt {
    //Переносим из Activity константы макисмального количества треков в истории, а также ключа SharedPreferences дл истории поиска
    companion object {
        const val COUNT_TRACK = 10
        const val HISTORY_KEY = "history_key"
    }

    //Для начала получим историю поиска
    //Заберем строки из файла SearchHistory
    private val gson = sharePref.getString(HISTORY_KEY, Gson().toJson(null))
    class Token : TypeToken<ArrayList<Track>>()
    private val listShare : ArrayList<Track> = if(gson == Gson().toJson(null)) ArrayList() else Gson().fromJson(gson, Token().type)

    override fun load(): List<Track> {
        return listShare.reversed()
    }

    override fun save(trackItem: Track) {
        //Получаем ID трека для проверка
        val trackID = trackItem.trackId
        //Если трек уже есть в истории
        if (listShare.any{trackID == it.trackId}) {
            listShare.removeIf{trackID == it.trackId}
            listShare.add(trackItem)
        } else {
            listShare.add(trackItem)
        }
        //Если треков больше 10
        if (listShare.size > COUNT_TRACK) {
            listShare.removeAt(0)
        }
        //Добавляем в историю
        val json = Gson().toJson(listShare)
        sharePref.edit().putString(HISTORY_KEY, json).apply()
    }

    override fun clearHistory() {
        listShare.clear()
        sharePref.edit().clear().apply()
    }


}