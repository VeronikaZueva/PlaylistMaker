package com.iclean.playlistmaker.search.data.models

//Класс-обработчик состояний ответа по типу Ошибка или Успех
//Мы создаем объект класса Error - если получаем один из типов ошибки
//Мы создаем объект класса Success - если запрос прошел успешно
//При этом, работаем с дженериками - то есть не привязываемся к типу данных

sealed class StateType<T>(val data: T? = null, val code : Int? = null) {
    class Error<T>(code: Int, data: T? = null) : StateType<T>(data, code)

    class Success<T>(data: T?) : StateType<T>(data)
}