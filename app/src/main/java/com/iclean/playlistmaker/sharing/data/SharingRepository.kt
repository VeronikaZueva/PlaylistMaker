package com.iclean.playlistmaker.sharing.data

interface SharingRepository {
    fun shareApp(link : String)
    fun sendMessage(email : String, subject : String, message : String)

    fun goToLink(policy : String)
}