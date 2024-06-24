package com.iclean.playlistmaker.player.data.dto

import com.iclean.playlistmaker.ITunesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTrack {
    private val itunesBaseUrl = "https://itunes.apple.com"

    //Запрос к API и Retrofit
    fun responseDataRetrofit(): ITunesApi? {
        val retrofit = Retrofit.Builder()
            .baseUrl(itunesBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ITunesApi::class.java)
    }
}