package com.iclean.playlistmaker.data.dto

import java.text.SimpleDateFormat
import java.util.Locale

open class DateFormat   {


    //Работа с датой и временем
    fun dateFormatTrack(model : String?) : String? {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault())
            .format(model?.toInt())
    }





}