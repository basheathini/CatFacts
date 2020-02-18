package com.basheathini.catfacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
class Cat(@PrimaryKey @ColumnInfo(name = "cat") val cat: String)