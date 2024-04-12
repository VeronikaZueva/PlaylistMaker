package com.iclean.playlistmaker

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

open class CheckStatus  {
    //Переменные для статусов
    lateinit var hideBlock : LinearLayout
    lateinit var statusImage : ImageView
    lateinit var statusText : TextView
    lateinit var additionalText : TextView
    lateinit var updateButton : MaterialButton
    lateinit var historyBlock : LinearLayout
    lateinit var historyText : TextView
    lateinit var historyButton : MaterialButton
    lateinit var reciclerViewHistoryTrack : RecyclerView
    fun showStatus(status: Status) {
        when (status) {
            Status.NONE -> {
                hideBlock.isVisible = false
                additionalText.isVisible = false
                updateButton.isVisible = false
                historyText.isVisible = false
                historyButton.isVisible = false
                historyBlock.isVisible = false
                reciclerViewHistoryTrack.isVisible = false
            }

            Status.SEARCH -> {
                hideBlock.isVisible = true
                additionalText.isVisible = false
                updateButton.isVisible = false
                historyText.isVisible = false
                historyButton.isVisible = false
                statusImage.setImageResource(R.drawable.search_none)
                statusText.setText(R.string.none_search)
                historyBlock.isVisible = false
                reciclerViewHistoryTrack.isVisible = false
            }

            Status.INTERNET -> {
                hideBlock.isVisible = true
                additionalText.isVisible = true
                updateButton.isVisible = true
                historyText.isVisible = false
                historyButton.isVisible = false
                statusImage.setImageResource(R.drawable.internet)
                statusText.setText(R.string.none_internet)
                historyBlock.isVisible = false
                reciclerViewHistoryTrack.isVisible = false
            }

            Status.HISTORY -> {
                hideBlock.isVisible = false
                historyBlock.isVisible = true
                historyText.isVisible = true
                historyButton.isVisible = true
                reciclerViewHistoryTrack.isVisible = true
            }
        }
    }
}