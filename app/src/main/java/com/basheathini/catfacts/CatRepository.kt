package com.basheathini.catfacts

import androidx.lifecycle.LiveData

class CatRepository(private val catDao: CatDao) {
    val allCats: LiveData<List<Cat>> = catDao.getAlphabetizedCats()

    suspend fun insert(cat: List<Cat>) {
        catDao.insert(cat)
    }
}