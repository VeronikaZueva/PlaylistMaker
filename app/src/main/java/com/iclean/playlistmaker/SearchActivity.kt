package com.iclean.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.iclean.playlistmaker.data.dto.RetrofitTrack
import com.iclean.playlistmaker.player.presentation.ui.TrackMethods
import com.iclean.playlistmaker.player.presentation.ui.PlayerActivity
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
        private const val CLICK_DEBOUNCE_DELAY = 2000L
    }

    val checkStatus = CheckStatus()
    val tracks = ArrayList<TrackResponse.Track>()
    private var historyTracks = ArrayList<TrackResponse.Track>(COUNT_TRACK)
    private val searchClass = SearchHistory()
    private val retrofit = RetrofitTrack()
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var clearButton : ImageButton
    private lateinit var searchInput : EditText
    private lateinit var runnable : Runnable
    private lateinit var progressBar: ProgressBar

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
        searchInput = findViewById(R.id.search_input)
        clearButton = findViewById(R.id.clear)
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


        //Добавляем трек в историю и переходим на его просмотр
        trackAdapter.onItemClick = { trackItem ->
            if(clickDebounce()) {
                searchInput.clearFocus()
                searchClass.addTrackInHistory(historyTracks, trackItem)
                routerAudioPlayer(trackItem)
                historyAdapter.notifyDataSetChanged()
            }
        }

        //Нажатие по треку в истории
        historyAdapter.onItemClick = { trackItem ->
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("trackObject", trackItem)
            startActivity(intent)
        }

        //Скрытые слои
        checkStatus.hideBlock = findViewById(R.id.hide_block)
        checkStatus.statusImage = findViewById(R.id.status_image)
        checkStatus.statusText = findViewById(R.id.status_text)
        checkStatus.additionalText = findViewById(R.id.additional_text)
        checkStatus.updateButton = findViewById(R.id.update_button)
        checkStatus.historyBlock = findViewById(R.id.history_block)
        checkStatus.historyText = findViewById(R.id.history_text)
        checkStatus.historyButton = findViewById(R.id.history_clear)
        progressBar = findViewById(R.id.progressBar)


        //Фокус
        searchInput.setOnFocusChangeListener { _, _ ->
            if (searchInput.hasFocus() && historyTracks.isNotEmpty()) {
                tracks.clear()
                checkStatus.reciclerViewHistoryTrack.adapter = historyAdapter
                historyAdapter.notifyDataSetChanged()
                checkStatus.showStatus(Status.HISTORY)
            }
        }


        //Работаем с TextWatcher
        searchInput.addTextChangedListener(
            beforeTextChanged = { _: CharSequence?, _: Int, _: Int, _: Int -> },
            onTextChanged = { p0: CharSequence?, _: Int, _: Int, _: Int ->
                searchText = p0.toString()

                if (!p0.isNullOrEmpty())
                {
                    p0.toString()
                    clearButton.visibility = View.VISIBLE
                    checkStatus.showStatus(Status.NONE)
                    searchDebounce()
                }
                else  {
                    if(searchInput.hasFocus() && historyTracks.isNotEmpty()) {

                        tracks.clear()
                        checkStatus.reciclerViewHistoryTrack.adapter = historyAdapter
                        historyAdapter.notifyDataSetChanged()
                        checkStatus.showStatus(Status.HISTORY)

                    }
                    checkStatus.showStatus(Status.NONE)
                    clearButton.visibility = View.GONE

                }
        },
            afterTextChanged = { _: Editable? -> })

        //Создаем запрос и выводим результат
         runnable = Runnable{
            tracks.clear()
            checkStatus.hideBlock.isVisible = false
            progressBar.visibility = View.VISIBLE
            retrofit.responseDataRetrofit()?.searchTrack(searchInput.text.toString())
                ?.enqueue(object : Callback<TrackResponse> {
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        if (response.code() == 200) {
                            tracks.clear()
                            progressBar.visibility = View.GONE
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
                        progressBar.visibility = View.GONE
                        checkStatus.showStatus(Status.INTERNET)
                    }
                })
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
            searchDebounce()

        }

        //Удаление истории
        checkStatus.historyButton.setOnClickListener {
            searchClass.clearHistory(historyTracks)
            historyAdapter.notifyDataSetChanged()
            checkStatus.showStatus(Status.NONE)
        }


    }

    //Задержки действий
    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if(isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({isClickAllowed = true}, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun searchDebounce() {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, CLICK_DEBOUNCE_DELAY)
    }


    //Переход на экран Аудиоплеер
    private fun routerAudioPlayer (trackItem : TrackResponse.Track) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("trackObject", trackItem)
        startActivity(intent)
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