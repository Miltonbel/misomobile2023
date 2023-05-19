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

class AddAlbumToArtistFormViewModel(application: Application, albumId: String) :  AndroidViewModel(application) {
    val albumId: String = albumId
    private val artistRepository = ArtistRepository(application)

    class Factory(val app: Application, val albumId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddAlbumToArtistFormViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddAlbumToArtistFormViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun addAlbumToArtist(navigate: (Int) -> Unit) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    artistRepository.addAlbumToArtist(albumId.toInt(), albumId.toInt())
                }
                navigate(albumId.toInt())
            } catch (e: Exception) {
                println("error")
            }
        }
    }
}