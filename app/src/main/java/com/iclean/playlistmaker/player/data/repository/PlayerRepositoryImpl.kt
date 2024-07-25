package com.iclean.playlistmaker.player.data.repository

import android.media.MediaPlayer
import com.iclean.playlistmaker.player.data.PlayerRepository

//Частично меняем функционал:
//1. Класс уже должен создаваться с переданной ссылкой в конструкторе
//2. Упростим функцию prepared и вынесем отдельно состояния плеера: setOnPrepare и setOnCompleted
//3. Функция StatusTimer относится к бизнес-логике и мы ее перенесем в domain.
// Здесь же нам просто потребуется метод mediaPlayerа - получение текущей позиции

class PlayerRepositoryImpl(private val previewUrl : String, runnable: Runnable) : PlayerRepository {

    private val mediaPlayer = MediaPlayer()

    override fun setOnPreparedListener(listener : MediaPlayer.OnPreparedListener) {
        setOnPreparedListener(listener)
    }
    override fun setOnCompletionListener(listener : MediaPlayer.OnCompletionListener) {
        setOnCompletionListener(listener)
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


}