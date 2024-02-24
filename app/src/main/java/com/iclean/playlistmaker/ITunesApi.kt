package com.iclean.playlistmaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ITunesApi {
    @GET("/search?entity=song&country=ru&{term}")
    fun searchTrack(@Path("term") term : String) : Call<TrackResponse>
}