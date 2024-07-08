package com.example.androidrecape.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidrecape.models.Qoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuotesViewModel : ViewModel() {

    private var index = 0

    private val quotes = mutableListOf<Qoute>()

    private val _qoutes: MutableLiveData<List<Qoute>> by lazy {
        MutableLiveData<List<Qoute>>()
    }

    var qouteList: LiveData<List<Qoute>> = _qoutes

    private val _currentQoute: MutableLiveData<Qoute> by lazy {
        MutableLiveData<Qoute>()
    }
    public val currentQuote: LiveData<Qoute> = _currentQoute


    fun loadQuotes() {
        viewModelScope.launch {
            delay(3000)
            quotes.add(
                Qoute(
                    1,
                    "The way to get started is to quit talking and begin doing.",
                    "Walt Disney"
                )
            )
            quotes.add(
                Qoute(
                    2,
                    "Success is not final, failure is not fatal: It is the courage to continue that counts.",
                    "Winston Churchill"
                )
            )
            quotes.add(
                Qoute(
                    3,
                    "Life is like riding a bicycle. To keep your balance, you must keep moving.",
                    "Albert Einstein"
                )
            )
            _qoutes.postValue(quotes)
            _currentQoute.postValue(quotes[index])
        }

    }

    fun getNext() {
        _currentQoute.postValue(quotes[++index])
    }

    fun getPrevious() {
        _currentQoute.postValue(quotes[--index])
    }
}