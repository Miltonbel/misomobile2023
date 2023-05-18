package com.example.vinyls.artist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AddAlbumToArtistFormViewModel(application: Application, artistId: String) :  AndroidViewModel(application) {
    val id: String = artistId
    private val artistRepository = ArtistRepository(application)

    class Factory(val app: Application, val artistId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddAlbumToArtistFormViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddAlbumToArtistFormViewModel(app, artistId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun addTrackToAlbum(body: JSONObject, navigate: (Int) -> Unit) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    artistRepository.addAlbumToArtist(id.toInt(),body)
                }
                navigate(id.toInt())
            } catch (e: Exception) {
                println("error")
            }
        }
    }
}