package com.example.vinyls.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddAlbumTracksFormViewModel(application: Application, albumId: String) :  AndroidViewModel(application) {
    val id: String = albumId

    class Factory(val app: Application, val albumId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddAlbumTracksFormViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddAlbumTracksFormViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}