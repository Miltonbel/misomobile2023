package com.example.vinyls.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.AlbumRepository
import com.example.vinyls.model.database.VinylRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AddAlbumTracksFormViewModel(application: Application, albumId: String) :  AndroidViewModel(application) {
    val id: String = albumId
    private val albumRepository = AlbumRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())

    class Factory(val app: Application, val albumId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddAlbumTracksFormViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddAlbumTracksFormViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun addTrackToAlbum(body: JSONObject, navigate: (Int) -> Unit) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    albumRepository.addTrackToAlbum(id.toInt(),body)
                }
                navigate(id.toInt())
            } catch (e: Exception) {
                println("error")
            }
        }
    }
}