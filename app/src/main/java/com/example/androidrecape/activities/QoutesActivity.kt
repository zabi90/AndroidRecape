package com.example.androidrecape.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidrecape.R
import com.example.androidrecape.models.Qoute
import com.example.androidrecape.viewmodels.QuotesViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class QoutesActivity : BaseActivity() {

    private lateinit var buttonNext : MaterialButton
    private lateinit var buttonPrevious : MaterialButton
    private lateinit var quotesText:MaterialTextView
    private lateinit var authorText:MaterialTextView


    private lateinit var viewModel: QuotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_qoutes)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setContentView(R.layout.activity_qoutes)
        authorText = findViewById(R.id.text_author)
        quotesText = findViewById(R.id.text_quote)
        buttonNext = findViewById(R.id.button_next)
        buttonPrevious = findViewById(R.id.button_previsious)
        viewModel = ViewModelProvider(this)[QuotesViewModel::class.java]
        viewModel.loadQuotes()
        setClickListener()
        viewModel.qouteList.observe(this, Observer {
            Toast.makeText(this, "Quotes loaded "+it.size, Toast.LENGTH_SHORT).show()
        })
        viewModel.currentQuote.observe(this, Observer {
            setQuotes(it)
        })
    }

    private fun setClickListener(){
        buttonPrevious.setOnClickListener {
           viewModel.getPrevious()
        }
        buttonNext.setOnClickListener {
            viewModel.getNext()
        }
    }
    private fun setQuotes(qoute: Qoute){
        quotesText.text = qoute.quote
        authorText.text = qoute.author
    }

    companion object{
        fun getLaunchIntent(context:Context) : Intent{
            return Intent(context, QoutesActivity::class.java)
        }
    }
}