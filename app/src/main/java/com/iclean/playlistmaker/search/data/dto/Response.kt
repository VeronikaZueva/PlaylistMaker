package com.iclean.playlistmaker.search.data.dto

import com.iclean.playlistmaker.search.data.models.ResposeCode
import com.iclean.playlistmaker.search.data.models.TrackDto

//Класс, определяющий формат ответа
//На выход принимаем количество найденных результатов и лист треков
class Response(val resultCount : Int, val results : List<TrackDto>) : ResposeCode()