package com.basheathini.catfacts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {

    //exposes the database via the methods
    @Query("SELECT * from cats ORDER BY cat ASC")
    fun getAlphabetizedCats(): LiveData<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: List<Cat>)

}