package com.iclean.playlistmaker.player

import com.iclean.playlistmaker.player.domain.repository.TrackMediaPlayerInterface
import com.iclean.playlistmaker.data.models.MediaPlayerState

class TrackMediaPlayerImpl : TrackMediaPlayerInterface {


    //Функции состояния воспроизведения
    override fun defaultPlayerState() : MediaPlayerState {
        return MediaPlayerState.STATE_DEFAULT
    }
    override fun preparePlayer() : MediaPlayerState {
        return MediaPlayerState.STATE_PREPARED
    }
    override fun startPlayer() : MediaPlayerState {
       return MediaPlayerState.STATE_PLAYING
    }
    override fun pausePlayer() : MediaPlayerState {
       return MediaPlayerState.STATE_PAUSED
    }

    override fun statusTimer() : String {
        return "00:00"
    }




    /*
    - Объянение логики: domain отвечает за основные функции приложения и реализацию ПРОСТЫХ
    интерфейсов, без уточнения данных и испоьзования SDK.
    - При этом, domail может запрашивать данные у Entities, Отправлять функцию в SDK через интерфейс
    и работать с Data слоем
    - domain - это конкретные действия: положить в корзину, оотправить сообщение и т.д.
    - В нашем примере, таких действий 3: подготовить плеер, запустить плеер, остановить плеер
    - При помощи playerState - мы работаем с Entities. При этом, правило зависимостей сохраняется.
    Entites все равно как их используют - это просто набор данных
     */

}