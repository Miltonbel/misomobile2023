package com.example.vinyls.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinyls.model.AlbumDBDao
import com.example.vinyls.model.AlbumRepository
import org.json.JSONObject
import java.lang.Appendable

class AddAlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val _album = MutableLiveData<AlbumDBDao>()

    val album: LiveData<AlbumDBDao>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val albumRepository = AlbumRepository(application)
    init {
        createNewAlbum()
    }

    private fun createNewAlbum(){
        val body = JSONObject(mapOf("name" to "Buscando América",
            "cover" to "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
            "releaseDate" to "1984-08-01T00:00:00-05:00",
            "description" to "some description",
            "genre" to "Salsa",
            "recordLabel" to "Elektra"))
        albumRepository.createAlbum({},{},body)
    }
}