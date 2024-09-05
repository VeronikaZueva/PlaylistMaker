package com.iclean.playlistmaker.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.data.dto.Request
import com.iclean.playlistmaker.search.data.models.ResposeCode

//??? Здесь пока не понимаю, как работать с context???
class RetrofitImpl(private val iTunesApi: ITunesApi,
                   private val context: Context) : NetworkClient {

    //Для передачи контекста
    override fun search(request: Any): ResposeCode {
        //Если нет интернета
        if(!isConnected()) {
            return ResposeCode().apply { stateResponse = -1 }
        }
        //Если задан неверный поисковый запрос (нарушен формат запроса), то возвращаем код 400
        if(request !is Request) {
            return ResposeCode().apply { stateResponse = 400 }
        }
        //Если все хорошо, составляем запрос
        val response = iTunesApi.search(request.expression).execute()
        val body = response.body()
        return body?.apply { stateResponse = response.code() }
            ?: ResposeCode().apply { stateResponse = response.code() }



    }

    //Проверяем соединение с сервером
    private fun isConnected() : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if(capabilities != null) {
            when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}