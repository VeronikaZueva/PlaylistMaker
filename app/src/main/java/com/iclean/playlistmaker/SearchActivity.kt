package com.iclean.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class SearchActivity :  AppCompatActivity() {

    //Константы
    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val SEARCH_DEF = ""
        const val COUNT_TRACK = 10
    }

    val checkStatus = CheckStatus()
    val tracks = ArrayList<TrackResponse.Track>()
    var historyTracks = ArrayList<TrackResponse.Track>(COUNT_TRACK)
    private val searchClass = SearchHistory()
    private val trackMethods = TrackMethods()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        //Вернуться домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            this.finish()
        }

        //Поле поиска
        val searchInput = findViewById<EditText>(R.id.search_input)
        val clearButton = findViewById<ImageButton>(R.id.clear)
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager


        //RecyclerViews
        checkStatus.reciclerViewHistoryTrack = findViewById(R.id.reciclerViewHistoryTrack)
        val recyclerViewTrack = findViewById<RecyclerView>(R.id.reciclerViewTrack)

        val historyAdapter = TrackAdapter(historyTracks)
        val trackAdapter = TrackAdapter(tracks)

        recyclerViewTrack.adapter = trackAdapter

        //SharedPreferences and Gson
        searchClass.fromJson(historyTracks)


        //Добавляем трек в историю
        trackAdapter.onItemClick = { trackItem ->
            searchInput.clearFocus()
            searchClass.addTrackInHistory(historyTracks, trackItem)
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("trackObject", trackItem)
            startActivity(intent)
            historyAdapter.notifyDataSetChanged()
        }

        //Нажатие по треку в истории
        historyAdapter.onItemClick = { trackItem ->
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("trackObject", trackItem)
            startActivity(intent)
        }

        //Placeholders и История поиска
        checkStatus.hideBlock = findViewById(R.id.hide_block)
        checkStatus.statusImage = findViewById(R.id.status_image)
        checkStatus.statusText = findViewById(R.id.status_text)
        checkStatus.additionalText = findViewById(R.id.additional_text)
        checkStatus.updateButton = findViewById(R.id.update_button)
        checkStatus.historyBlock = findViewById(R.id.history_block)
        checkStatus.historyText = findViewById(R.id.history_text)
        checkStatus.historyButton = findViewById(R.id.history_clear)


        //Фокус
        searchInput.setOnFocusChangeListener { _, _ ->
            if (searchInput.hasFocus() && historyTracks.isNotEmpty()) {
                checkStatus.reciclerViewHistoryTrack.adapter = historyAdapter
                historyAdapter.notifyDataSetChanged()
                checkStatus.showStatus(Status.HISTORY)
            }
        }

        //Обработчик введенных значений
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!p0.isNullOrEmpty())
                {
                    p0.toString()
                    clearButton.visibility = View.VISIBLE
                    checkStatus.showStatus(Status.NONE)

                }
                else  {
                    if(searchInput.hasFocus() && historyTracks.isNotEmpty()) {
                        checkStatus.reciclerViewHistoryTrack.adapter = historyAdapter
                        historyAdapter.notifyDataSetChanged()
                        checkStatus.showStatus(Status.HISTORY)
                    }
                    checkStatus.showStatus(Status.NONE)
                    clearButton.visibility = View.GONE

                }

                searchText = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        searchInput.addTextChangedListener(simpleTextWatcher)

        //Создаем запрос и выводим результат
        fun createResult() {
            tracks.clear()
            checkStatus.hideBlock.isVisible = false
            trackMethods.itunesService?.searchTrack(searchInput.text.toString())
                ?.enqueue(object : Callback<TrackResponse> {
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        if (response.code() == 200) {
                            tracks.clear()
                            tracks.addAll(response.body()?.results!!)
                            trackAdapter.notifyDataSetChanged()
                            if (tracks.isEmpty()) {
                                checkStatus.showStatus(Status.SEARCH)
                            }
                        } else {
                            checkStatus.showStatus(Status.INTERNET)
                        }
                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        checkStatus.showStatus(Status.INTERNET)
                    }
                })
    }

        //Выводим список RecyclerView
        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                    createResult()

            }
            false
        }

        //Очищаем поле поиска
        clearButton.setOnClickListener {
            tracks.clear()
            trackAdapter.notifyDataSetChanged()
            searchInput.setText("")
            inputMethodManager?.hideSoftInputFromWindow(clearButton.windowToken, 0)
            searchInput.clearFocus()
        }
        searchInput.setText(searchText)

        //Обновляем запрос
        checkStatus.updateButton.setOnClickListener {
            createResult()

        }

        //Удаление истории
        checkStatus.historyButton.setOnClickListener {
            searchClass.clearHistory(historyTracks)
            historyAdapter.notifyDataSetChanged()
            checkStatus.showStatus(Status.NONE)
        }


    }

    //Сохранение данных
    private var searchText : String = SEARCH_DEF

    override fun onSaveInstanceState(outState:Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchText)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT, SEARCH_DEF)
    }


}