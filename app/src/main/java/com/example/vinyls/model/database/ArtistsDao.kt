package com.example.vinyls.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinyls.model.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artists_table")
    fun getArtists():List<Artist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(artist: Artist)

    @Query("DELETE FROM artists_table")
    fun deleteAll(): Int
}