package com.iclean.playlistmaker

/*
* trackName: String // Название композиции
* artistName: String // Имя исполнителя
* trackTime: String // Продолжительность трека
* artworkUrl100: String // Ссылка на изображение обложки
*  */
data class Track (val trackName: String,
             val artistName: String,
             val trackTime: String,
             val artworkUrl100: String)     {

    //Создаем Mock-object для нашего тестового статического списка
    val mockArrayList : ArrayList<Track> = arrayListOf(
        Track (
            "Название композиции: Smells Like Teen Spirit",
            "Имя исполнителя: Nirvana",
            "Продолжительность трека: 5:01",
            "Ссылка на изображение обложки: https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
        ),
        Track (
            "Название композиции: Billie Jean",
            "Имя исполнителя: Michael Jackson",
            "Продолжительность трека: 4:35",
    "Ссылка на изображение обложки: https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
        ),
        Track (
    "Название композиции: Stayin' Alive",
    "Имя исполнителя: Bee Gees",
    "Продолжительность трека: 4:10",
    "Ссылка на изображение обложки: https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
        ),
        Track (
    "Название композиции: Whole Lotta Love",
    "Имя исполнителя: Led Zeppelin",
    "Продолжительность трека: 5:33",
    "Ссылка на изображение обложки: https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
        ),
        Track (
    "Название композиции: Sweet Child O'Mine",
    "Имя исполнителя: Guns N' Roses",
    "Продолжительность трека: 5:03",
    "Ссылка на изображение обложки: https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
        )
    )

}

