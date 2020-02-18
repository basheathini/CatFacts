package com.basheathini.catfacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CatRepository
    val allCats: LiveData<List<Cat>>

    init {
        val catsDao = CatRoomDatabase.getDatabase(application,viewModelScope).catDao()
        repository = CatRepository(catsDao)
        allCats = repository.allCats
    }
    fun insert(cat: List<Cat>) = viewModelScope.launch {
        repository.insert(cat)
    }
}