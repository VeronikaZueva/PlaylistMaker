package com.iclean.playlistmaker.search.ui

import android.content.Intent
import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.iclean.playlistmaker.databinding.FragmentSearchBinding
import com.iclean.playlistmaker.player.ui.PlayerActivity
import com.iclean.playlistmaker.search.domain.api.TrackClick
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.search.presentation.SearchViewModel
import com.iclean.playlistmaker.search.ui.models.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    //Константы
    companion object {
        const val SEARCH_DEF = ""
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val DELAY = 2000L
    }

    private var isClickAllowed = true
    private var searchText: String = SEARCH_DEF
    private lateinit var progressBar: ProgressBar

    //Создаем ViewModel и Binding
    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var binding: FragmentSearchBinding

    //Обозначаем Адаптеры для Списока поиска и Списка истории
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var historyAdapter: HistoryAdapter

    //Покдлючаемся к классу, регулирующему отображение блоков
    val checkStatus = CheckStatus()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Кликнуть на трек - добавляем трек в историю и переходим на его просмотр
        val trackClick = object : TrackClick {
            override fun getTrack(track: Track) {
                checkStatus.showStatus(Status.NONE)
                if (clickDebounce()) {
                    viewModel.save(track)
                    //Сохранили трек и переходим к нему через Intent
                    val intent = Intent(requireContext(), PlayerActivity::class.java)
                    intent.putExtra("trackObject", Gson().toJson(track))
                    startActivity(intent)
                }
            }
        }


        //Задаем список Истории
        historyAdapter = HistoryAdapter(trackClick)
        historyAdapter.submitList(listOf())

        //Задаем список Поиска
        trackAdapter = TrackAdapter(trackClick)
        trackAdapter.submitList(listOf())


        //Определяем скрытые блоки
        checkStatus.hideBlock = binding.hideBlock
        checkStatus.statusImage = binding.statusImage
        checkStatus.statusText = binding.statusText
        checkStatus.additionalText = binding.additionalText
        checkStatus.updateButton = binding.updateButton
        checkStatus.historyBlock = binding.historyBlock
        checkStatus.historyText = binding.historyText
        checkStatus.historyButton = binding.historyClear
        progressBar = binding.progressBar

        //Задаем начальное значение статуса
        checkStatus.showStatus(Status.NONE)

        //ВОТ ЗДЕСЬ ГЛАВНЫЙ ОБРАБОТЧИК
        viewModel.getResult().observe(viewLifecycleOwner) {
            //Работаем с LiveData
            progressBar.visibility = View.GONE
            trackAdapter.submitList(it.trackList)
            historyAdapter.submitList(it.historyList)
            //Если запрос не пустой
            if ((it.code != -2) and binding.searchInput.text.isNotEmpty()) {
                binding.reciclerViewTrack.adapter = trackAdapter
                setCodeResponse(it.code)
                if ((it.trackList.isNullOrEmpty()) and (it.code != -1)) {
                    //Показываем ничего не нашлось
                    checkStatus.showStatus(Status.SEARCH)
                    binding.reciclerViewTrack.isVisible = false
                } else {
                    checkStatus.showStatus(Status.NONE)
                    binding.reciclerViewTrack.isVisible = true
                }
            } else {
                if (it.historyList.isEmpty()) {
                    checkStatus.showStatus(Status.NONE)
                } else {
                    checkStatus.showStatus(Status.HISTORY)
                    binding.reciclerViewHistoryTrack.adapter = historyAdapter
                }

            }
        }

        //Очищаем историю
        checkStatus.historyButton.setOnClickListener {
            viewModel.clearHistory()
            checkStatus.showStatus(Status.NONE)
        }

        //Фокус - показываем историю Поиска, когда поле Поиска в фокусе - РАБОТАЕТ
        binding.searchInput.setOnFocusChangeListener { _, _ ->
            viewModel.load()
            checkStatus.showStatus(Status.HISTORY)
        }

        //Очищаем поле поиска - РАБОТАЕТ
        binding.clear.setOnClickListener {
            binding.searchInput.setText(SEARCH_DEF)
            viewModel.load()
            checkStatus.showStatus(Status.HISTORY)
        }

        binding.reciclerViewTrack.layoutManager = LinearLayoutManager(requireContext())
        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.searchInput.text.isNotEmpty()) {
                    searchDebounce()
                } else {
                    viewModel.load()
                }
                @Suppress("UNUSED_EXPRESSION")
                true
            }
            false
        }

        //Работаем с TextWatcher - РАБОТАЕТ
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchText = p0.toString()
                if (!p0.isNullOrEmpty()) {
                    p0.toString()
                    binding.clear.visibility = View.VISIBLE
                    checkStatus.showStatus(Status.NONE)
                    searchDebounce()
                } else {
                    checkStatus.showStatus(Status.NONE)
                    binding.clear.visibility = View.GONE
                    viewModel.load()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.searchInput.addTextChangedListener(textWatcher)
    }

    //Выполняем поиск - основной процесс
    fun search() {
        checkStatus.hideBlock.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        sendRequest()
    }


    //Выполняем поиск - отдаем поисковый запрос
    private fun sendRequest() {
        val searchInput = binding.searchInput
        viewModel.search(searchInput.text.toString())
    }

    //Обработчик ответа от сервера
    private fun setCodeResponse(code : Int?) {
        when(code) {
            200 -> checkStatus.showStatus(Status.NONE)
            -1 -> checkStatus.showStatus(Status.INTERNET)
        }
    }


    //Задержки действий
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(DELAY)
                isClickAllowed = true
            }

        }
        return current
    }

    private fun searchDebounce() {
        checkStatus.showStatus(Status.NONE)
        binding.reciclerViewTrack.visibility = View.GONE
        search()
        progressBar.visibility = View.VISIBLE
    }



    //Сохранение состояний
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchText)
    }


}