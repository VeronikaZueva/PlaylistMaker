package com.iclean.playlistmaker.search.data.dto

import android.os.Handler
import android.os.Looper

//Работаем с Handler
//Здесь нам нужно объявить Handler  реализовать методы задержки и удаления
class HandlerController  {
    private val handler : Handler = Handler(Looper.getMainLooper())
    fun postDelay(runnable: Runnable, delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    fun removeCallback(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }
}