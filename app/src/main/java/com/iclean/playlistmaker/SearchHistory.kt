package com.iclean.playlistmaker


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iclean.playlistmaker.App.Companion.HISTORY_KEY
import com.iclean.playlistmaker.App.Companion.sharedPreferences
import java.util.ArrayList

class SearchHistory {

    private val gson = Gson()

    //Получаем историю поиска из SharedPreference
    fun fromJson(historyTracks : ArrayList<TrackResponse.Track>) {
        val fromJson = sharedPreferences.getString(HISTORY_KEY, null)

        if(fromJson != null) {
            val turnsType = object : TypeToken<ArrayList<TrackResponse.Track>>() {}.type
            val prefList = gson.fromJson<ArrayList<TrackResponse.Track>>(fromJson, turnsType)
            historyTracks.clear()
            historyTracks.addAll(prefList)
        }
    }

    //Добавляем трек в историю
    fun addTrackInHistory(historyTracks : ArrayList<TrackResponse.Track>, trackItem : TrackResponse.Track) {
        if (historyTracks.contains(trackItem)) {
            historyTracks.remove(trackItem)
        }
        historyTracks.add(0, trackItem)
        if (historyTracks.size > 10) {
            historyTracks.removeLast()
        }
        val json = gson.toJson(historyTracks)
        sharedPreferences.edit().putString(HISTORY_KEY, json).apply()
    }

    //Очищаем историю поиска
    fun clearHistory(historyTracks: ArrayList<TrackResponse.Track>) {
        historyTracks.clear()
        sharedPreferences.edit().clear().apply()
    }

}