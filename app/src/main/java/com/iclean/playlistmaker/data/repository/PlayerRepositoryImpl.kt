package com.iclean.playlistmaker.data.repository

import android.media.MediaPlayer
import com.iclean.playlistmaker.player.presentation.ui.TrackMethods
import com.iclean.playlistmaker.player.TrackMediaPlayerImpl
import com.iclean.playlistmaker.data.models.MediaPlayerState
import com.iclean.playlistmaker.player.domain.OnMediaPlayerStateChangeListener
import com.iclean.playlistmaker.player.domain.repository.PlayerRepository

class PlayerRepositoryImpl : PlayerRepository {

    private val mediaPlayer = MediaPlayer()
    private val impl = TrackMediaPlayerImpl()
    private val trackMethods = TrackMethods()
    private lateinit var onMediaPlayerStateChangeListener : OnMediaPlayerStateChangeListener
    override var state : MediaPlayerState = impl.defaultPlayerState()

    override fun setOnStateChangeListener(onMediaPlayerStateChangeListener: OnMediaPlayerStateChangeListener) {
        this.onMediaPlayerStateChangeListener = onMediaPlayerStateChangeListener
    }
    override fun preparePlayer(previewUrl : String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onMediaPlayerStateChangeListener.onChange(MediaPlayerState.STATE_PREPARED)
        }
        mediaPlayer.setOnCompletionListener {
            onMediaPlayerStateChangeListener.onChange(MediaPlayerState.STATE_COMPLETED)
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
            MediaPlayerState.STATE_PLAYING -> {
                val current = mediaPlayer.currentPosition.toString()
                trackMethods.dateFormatTrack(current)
            }
            else -> {
                impl.statusTimer()
            }

        }
    }

    override fun release() {mediaPlayer.release()}






}