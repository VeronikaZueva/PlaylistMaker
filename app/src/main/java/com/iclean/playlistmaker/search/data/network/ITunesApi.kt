package com.iclean.playlistmaker.search.data.network

import com.iclean.playlistmaker.search.data.dto.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Интерфейс, задающий ссылку для передачи в Retofit. В последующем, можно сменить на другой API
interface ITunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<Response>
}