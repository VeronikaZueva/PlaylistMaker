package com.iclean.playlistmaker.sharing.domain.impl

import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.general.Creator
import com.iclean.playlistmaker.sharing.domain.SharingRepository
import com.iclean.playlistmaker.sharing.domain.SharingInteractor

class SharingInteractorImpl(private val repository : SharingRepository) : SharingInteractor {
    private val app = Creator.app
    override fun shareApp() {
        repository.shareApp(getLinkApp())
    }

    override fun sendMessage() {
        repository.sendMessage(getEmail(), getSubject(), getMessage())
    }

    override fun goToLink() {
        repository.goToLink(getLinkPolicy())
    }

    override fun getLinkApp() : String {
        return app.getString(R.string.shareMessage)
    }

    override fun getEmail(): String {
        return app.getString(R.string.receiver)
    }
    override fun getSubject(): String {
        return app.getString(R.string.subject)
    }
    override fun getMessage(): String {
        return app.getString(R.string.mail_text)
    }

    override fun getLinkPolicy() : String {
        return app.getString(R.string.link_address)
    }
}