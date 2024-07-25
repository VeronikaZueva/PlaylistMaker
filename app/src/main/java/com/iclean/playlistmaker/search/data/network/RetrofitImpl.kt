package com.iclean.playlistmaker.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.data.dto.Request
import com.iclean.playlistmaker.search.data.models.ResposeCode
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Наша библиотека для поиска. Для удобства в названии указываем имя библиотеки и Impl - что она реализует определенный интерфейс
//Так как мы будем работать с определенным контекстом, то его передаем при создании класса
//Задачей Ретрофит - взаимодействие с конкретным сервером, а потому ссылку на api, к которому мы обращаемся, тоже нужно передать в конкструктор
//В будущем, возможно, мы будем использовать другое API, а потому ссылку на него, лучше также сделать отдельным классом

class RetrofitImpl(private val context: Context) : NetworkClient {
   private val iTunesLink = "https://itunes.apple.com"


    //Для передачи контекста
    //Строим Retrofit по образцу
    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesLink)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesApi = retrofit.create(ITunesApi::class.java)

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