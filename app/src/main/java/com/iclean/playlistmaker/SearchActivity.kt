package com.iclean.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


class SearchActivity : AppCompatActivity() {

    //Константы
    companion object {
        val tracks = ArrayList<TrackResponse.Track>()
        var historyTracks = ArrayList<TrackResponse.Track>(10)
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val SEARCH_DEF = ""
        const val HISTORY_KEY = "history_key"
    }
    //Задаем параметры Retrofit
    private val itunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesService = retrofit.create(ITunesApi::class.java)

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
        val reciclerViewHistoryTrack = findViewById<RecyclerView>(R.id.reciclerViewHistoryTrack)
        val historyAdapter = HistoryAdapter(historyTracks)
        reciclerViewHistoryTrack.adapter = historyAdapter

        //SharedPreferences and Gson
        val sharedPref = getSharedPreferences(App.SETTING_PARAMS, MODE_PRIVATE)
        val gson = Gson()
        val fromJson = sharedPref.getString(HISTORY_KEY, null)
        if(fromJson != null) {
            val turnsType = object : TypeToken<ArrayList<TrackResponse.Track>>() {}.type
            val prefList = gson.fromJson<ArrayList<TrackResponse.Track>>(fromJson, turnsType)
            historyTracks.addAll(prefList)
        }


        val recyclerViewTrack = findViewById<RecyclerView>(R.id.reciclerViewTrack)
        val trackAdapter = TrackAdapter(tracks)
        recyclerViewTrack.adapter = trackAdapter

        //Добавляем трек в историю
        trackAdapter.onItemClick = { trackItem ->
            searchInput.clearFocus()

            fun addTrack (trackItem : TrackResponse.Track) {
                historyTracks.add(0, trackItem)
                val json = gson.toJson(historyTracks)
                sharedPref.edit().putString(HISTORY_KEY, json).apply()
            }

            if(historyTracks.contains(trackItem)) {
                historyTracks.remove(trackItem)
                addTrack(trackItem)
            } else {
                addTrack(trackItem)
            }

        }

        //Placeholders и История поиска
        val hideBlock = findViewById<LinearLayout>(R.id.hide_block)
        val statusImage = findViewById<ImageView>(R.id.status_image)
        val statusText = findViewById<TextView>(R.id.status_text)
        val additionalText = findViewById<TextView>(R.id.additional_text)
        val updateButton = findViewById<MaterialButton>(R.id.update_button)
        val historyBlock = findViewById<LinearLayout>(R.id.history_block)
        val historyText = findViewById<TextView>(R.id.history_text)
        val historyButton = findViewById<MaterialButton>(R.id.history_clear)


        //Статусы
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

        //Фокус
        searchInput.setOnFocusChangeListener { _, _ ->
            if (searchInput.hasFocus() && historyTracks.isNotEmpty()) {
                historyAdapter.notifyDataSetChanged()
                showStatus(Status.HISTORY)
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
                    showStatus(Status.NONE)

                }
                else  {
                    if(searchInput.hasFocus() && historyTracks.isNotEmpty()) {
                        historyAdapter.notifyDataSetChanged()
                        showStatus(Status.HISTORY)
                    }
                    showStatus(Status.NONE)
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
            hideBlock.isVisible = false
        itunesService.searchTrack(searchInput.text.toString())
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        tracks.clear()
                        tracks.addAll(response.body()?.results!!)
                        trackAdapter.notifyDataSetChanged()
                        if (tracks.isEmpty()) {
                            showStatus(Status.SEARCH)
                        }
                    } else {
                        showStatus(Status.INTERNET)
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    showStatus(Status.INTERNET)
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
        updateButton.setOnClickListener {
            createResult()

        }

        //Удаление истории
        historyButton.setOnClickListener {
            historyTracks.clear()
            sharedPref.edit().clear().apply()
            historyAdapter.notifyDataSetChanged()
            showStatus(Status.NONE)
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