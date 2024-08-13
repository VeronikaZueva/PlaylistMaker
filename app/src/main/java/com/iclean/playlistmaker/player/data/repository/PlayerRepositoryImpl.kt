package com.iclean.playlistmaker.player.data.repository

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.iclean.playlistmaker.player.domain.OnCompletionListener
import com.iclean.playlistmaker.player.domain.OnPreparedListener
import com.iclean.playlistmaker.player.domain.PlayerRepository

//Частично меняем функционал:
//1. Класс уже должен создаваться с переданной ссылкой в конструкторе
//2. Упростим функцию prepared и вынесем отдельно состояния плеера: setOnPrepare и setOnCompleted
//3. Функция StatusTimer относится к бизнес-логике и мы ее перенесем в domain.
// Здесь же нам просто потребуется метод mediaPlayerа - получение текущей позиции

class PlayerRepositoryImpl(private val previewUrl : String, private val runnable: Runnable) :
    PlayerRepository {

    private val mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())

    //Медиаплеер
    override fun setOnPreparedListener() {
        object : OnPreparedListener {
            override fun onPrepared()   {
                mediaPlayer.run {
                    setOnPreparedListener()
                }
            }
        }
    }
    override fun setOnCompletionListener(listener: OnCompletionListener) {
        mediaPlayer.setOnCompletionListener { listener() }
    }
    override fun preparePlayer() {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
    }
    override fun startPlayer() {
        mediaPlayer.start()
    }

    override fun pausePlayer(){
        mediaPlayer.pause()
    }

    override fun release() {mediaPlayer.release()}

    override fun getCurrentPosition() : Int {
        return mediaPlayer.currentPosition
    }

    //Handler
    override fun postTimerDelay(delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    override fun removeCallback() {
        handler.removeCallbacks(runnable)
    }

    override fun removeCallbacksAndMessages() {
        handler.removeCallbacksAndMessages(runnable)
    }

}






