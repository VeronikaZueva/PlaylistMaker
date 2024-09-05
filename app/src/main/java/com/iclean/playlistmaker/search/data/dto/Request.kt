package com.iclean.playlistmaker.search.data.dto

//Класс, отвечающий за формат данных поискового запроса
//данный класс - информационный, он задает правила
//В качестве параметра мы передаем определенное выражение
data class Request (val expression : String)