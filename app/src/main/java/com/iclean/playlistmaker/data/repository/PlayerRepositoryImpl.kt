package com.iclean.playlistmaker.data.repository

import android.media.MediaPlayer
import com.iclean.playlistmaker.player.presentation.ui.TrackMethods
import com.iclean.playlistmaker.player.TrackMediaPlayerImpl
import com.iclean.playlistmaker.data.models.MediaPlayerState
import com.iclean.playlistmaker.player.domain.repository.PlayerRepository

class PlayerRepositoryImpl : PlayerRepository {

    private val mediaPlayer = MediaPlayer()
    private val impl = TrackMediaPlayerImpl()
    private val trackMethods = TrackMethods()
    override var state : MediaPlayerState = impl.defaultPlayerState()


    override fun preparePlayer(previewUrl : String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            state = impl.preparePlayer()
        }
        mediaPlayer.setOnCompletionListener {
            state = impl.preparePlayer()
        }
    }
    override fun startPlayer() {
        mediaPlayer.start()
        state = impl.startPlayer()
    }

    override fun pausePlayer(){
        mediaPlayer.pause()
        state = impl.pausePlayer()
    }


    override fun statusTimer(statePlayer : MediaPlayerState): String? {
        return when (statePlayer) {
            MediaPlayerState.STATE_DEFAULT, MediaPlayerState.STATE_PREPARED  -> impl.statusTimer()
            else -> {
                val current = mediaPlayer.currentPosition.toString()
                trackMethods.dateFormatTrack(current)
            }
        }
    }

    override fun release() {mediaPlayer.release()}





    /*
   * В Data слое мы работаем с конкретныи данными, с конкретной библиотекой.
   * В данном случае, мы работаем с МедиаПлеером.
   * Тут мы должны описать конкретные методы и состояния медиаплеера - MediaPlayer()
    * */


}