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

class AddAlbumToArtistFormViewModel(application: Application) :  AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)

    class Factory(val app: Application, val artistId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddAlbumToArtistFormViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddAlbumToArtistFormViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun addAlbumToArtist(navigate: (Int) -> Unit, albumId:String,artistId:String) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    artistRepository.addAlbumToArtist(albumId.toInt(), artistId.toInt())
                }
                navigate(artistId.toInt())
            } catch (e: Exception) {
                println("error")
            }
        }
    }
}