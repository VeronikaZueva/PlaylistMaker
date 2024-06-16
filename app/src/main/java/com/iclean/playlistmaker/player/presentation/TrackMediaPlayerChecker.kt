package com.iclean.playlistmaker.player.presentation

import android.media.MediaPlayer
import com.iclean.playlistmaker.data.TrackMethods
import com.iclean.playlistmaker.player.domain.impl.TrackMediaPlayerImpl
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState

class TrackMediaPlayerChecker : TrackMediaPlayerCheckerInterface {

    private val mediaPlayer = MediaPlayer()
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
    override fun startPlayer() {
       // mediaPlayer.start()
        state = impl.pausePlayer()
    }

    override fun pausePlayer()   {
      //  mediaPlayer.pause()
        state = impl.startPlayer()
    }
    override fun playControl() : Boolean {
        return when(state) {
            MediaPlayerState.STATE_PLAYING -> {
                startPlayer()
                false
            }

            else -> {
                pausePlayer()
                true
            }
        }

    }

    override fun statusTimer(): String? {
        return when (state) {
            MediaPlayerState.STATE_DEFAULT, MediaPlayerState.STATE_PREPARED  -> impl.statusTimer()
            else -> {
                val current = mediaPlayer.currentPosition.toString()
                trackMethods.dateFormatTrack(current)
            }
        }
    }

    override fun release() {mediaPlayer.release()}


    override fun getState() : MediaPlayerState {return state}

    /*
    * - presents содержит те действия, которые вляют на бизнес логику, и связывают функции domain
    * и ui объекты
    * - Здесь мы можем работать с Android SDK и реализовывать более сложные интерфейсы
    * - Слой Domain может, как запрашивать информацию у нас, так и получать ее от нас
    * */


}