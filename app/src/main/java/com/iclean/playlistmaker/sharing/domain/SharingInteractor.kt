package com.iclean.playlistmaker.sharing.domain

interface SharingInteractor {
    fun shareApp()
    fun sendMessage()
    fun goToLink()

    fun getLinkApp() : String
    fun getEmail() : String
    fun getSubject() : String
    fun getMessage() : String

    fun getLinkPolicy() : String
}