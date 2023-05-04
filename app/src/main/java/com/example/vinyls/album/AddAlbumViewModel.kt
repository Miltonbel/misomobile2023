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
    init {}

     fun createNewAlbum(body:JSONObject){
        albumRepository.createAlbum({},{},body)
    }
}