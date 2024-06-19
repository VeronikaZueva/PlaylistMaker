package com.iclean.playlistmaker.data.repository

import android.media.MediaPlayer
import com.iclean.playlistmaker.player.presentation.ui.TrackMethods
import com.iclean.playlistmaker.player.domain.impl.TrackMediaPlayerImpl
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState
import com.iclean.playlistmaker.player.domain.repository.PlayerRepository

class PlayerRepositoryImpl : PlayerRepository {

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private val impl = TrackMediaPlayerImpl()
    private val trackMethods = TrackMethods()
    private var state : MediaPlayerState = impl.defaultPlayerState()


    override fun preparePlayer(url : String) : MediaPlayerState  {
        with(mediaPlayer) {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                state = impl.preparePlayer(url)
            }
            setOnCompletionListener {
                state = impl.preparePlayer(url)
            }
            return state
        }
    }
    override fun startPlayer() : MediaPlayerState {
        mediaPlayer.pause()
        state = impl.pausePlayer()
        return state
    }

    override fun pausePlayer() : MediaPlayerState  {
        mediaPlayer.start()
        state = impl.startPlayer()
        return state
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

    override fun getDefaultState() : MediaPlayerState {
        return impl.defaultPlayerState()
    }
    override fun getState(isEnable : Boolean) : MediaPlayerState {
        return if(isEnable) impl.startPlayer() else impl.pausePlayer()
    }

    /*
   * В Data слое мы работаем с конкретныи данными, с конкретной библиотекой.
   * В данном случае, мы работаем с МедиаПлеером.
   * Тут мы должны описать конкретные методы и состояния медиаплеера - MediaPlayer()
    * */


}