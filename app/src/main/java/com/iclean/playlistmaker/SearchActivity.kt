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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Вернуться домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        //Поле поиска
        val searchInput = findViewById<EditText>(R.id.search_input)
        val clearButton = findViewById<ImageButton>(R.id.clear)
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        clearButton.setOnClickListener {
            searchInput.setText("")
            inputMethodManager?.hideSoftInputFromWindow(clearButton.windowToken, 0)
            searchInput.clearFocus()
        }

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



}