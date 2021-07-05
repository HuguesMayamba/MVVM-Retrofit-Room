package com.hugues.swars.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(character: Character): Long

    @Query("SELECT * FROM characters")
    fun getAllCharacter(): LiveData<List<Character>>

    @Update
    suspend fun updateCharacter(character: Character)
}