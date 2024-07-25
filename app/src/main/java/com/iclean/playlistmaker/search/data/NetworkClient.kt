package com.iclean.playlistmaker.search.data

import com.iclean.playlistmaker.search.data.models.ResposeCode


//Интерфейс, задающий правила для библиотеки, которая будет работать с сервером
//Задача данной библиотеки - передавать и возвращать поисковый запрос/ответ
//При этом, запрос может быть в разных форматах, в зависимости от используемой библиотеки
//А для ответа сервера мы будем использовать наш класс Response - задающий формат ответа
//Поэтому в параметры поискового метода мы передаем запрос, а возвращаем ответ приведенного типа

interface NetworkClient {
    fun search(request : Any) : ResposeCode

}