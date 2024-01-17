package com.iclean.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton


class SearchActivity : AppCompatActivity() {

    //Отработка Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Вернуться домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        //Получение полей
        val searchInput = findViewById<EditText>(R.id.search_input)
        val clearButton = findViewById<ImageButton>(R.id.clear)
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        //Очистка данных
        clearButton.setOnClickListener {
            searchInput.setText("")
            inputMethodManager?.hideSoftInputFromWindow(clearButton.windowToken, 0)
            searchInput.clearFocus()
        }

        searchInput.setText(searchText)

        //Получение значений текстового поля, которые ввел Пользователь
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    p0.toString()
                    clearButton.visibility = View.VISIBLE
                }
                else {clearButton.visibility = View.GONE}
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        searchInput.addTextChangedListener(simpleTextWatcher)
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

    //Константы
    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val SEARCH_DEF = ""
    }



}