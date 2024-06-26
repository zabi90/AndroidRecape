package com.example.androidrecape.viewmodels

import androidx.lifecycle.ViewModel
import com.example.androidrecape.models.Qoute

class QuotesViewModel : ViewModel() {

    private var index  = 0

    private val quotes = mutableListOf<Qoute>()

    fun loadQuotes(){
        quotes.add(Qoute(1,"The way to get started is to quit talking and begin doing.","Walt Disney"))
        quotes.add(Qoute(2,"Success is not final, failure is not fatal: It is the courage to continue that counts.","Winston Churchill"))
        quotes.add(Qoute(3,"Life is like riding a bicycle. To keep your balance, you must keep moving.","Albert Einstein"))
    }
    fun currentQoute() : Qoute{
        return quotes[index]
    }

    fun getNext() : Qoute{
        return quotes[++index]
    }

    fun getPrevious() : Qoute{
        return quotes[--index]
    }
}