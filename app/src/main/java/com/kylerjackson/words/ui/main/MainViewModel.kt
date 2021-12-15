package com.kylerjackson.words.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kylerjackson.words.WordsRepository

class MainViewModel : ViewModel() {

    //Creates a new repository that connects to the Oxford Languages API
    val repository = WordsRepository()

    val word:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val definition:MutableLiveData<ArrayList<String>> by lazy{
        MutableLiveData<ArrayList<String>>()
    }

    val etymologies:MutableLiveData<ArrayList<String>> by lazy{
        MutableLiveData<ArrayList<String>>()
    }

    //This gives the searched word text widget a starting text of 'Example
    //This constructor also gives the repository the current instance of the viewModel
    init{
        this.word.value = "Example"
        repository.setMainViewModel(this)
    }

    //passes a word to the repository
    fun searchWord(word: String){
        repository.getData(word)
    }

}