package com.iclean.playlistmaker.player.data.dto

import android.os.Handler
import android.os.Looper

//Аналогично, как работали с Handler с пакете Search, прорабатываем его здесь

class HandlerController {
    private val handler : Handler = Handler(Looper.getMainLooper())
    private lateinit var run: Runnable
    fun postDelay(runnable: Runnable, delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    fun postTimerDelay(delay: Long) {
        handler.postDelayed(run, delay)
    }

    fun removeCallbacksAndMessages() {
        handler.removeCallbacksAndMessages(null)
    }

    fun removeCallback() {
        handler.removeCallbacks(run)
    }


}