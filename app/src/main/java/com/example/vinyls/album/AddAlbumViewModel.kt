package com.example.vinyls.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.AlbumDBDao
import com.example.vinyls.model.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AddAlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val _album = MutableLiveData<AlbumDBDao?>()

    val album: MutableLiveData<AlbumDBDao?>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val albumRepository = AlbumRepository(application)
    init {}

    fun createNewAlbum(body: JSONObject, callback: (Int)-> Unit){

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val data = withContext(Dispatchers.IO) {
                    albumRepository.createAlbum(body)
                }
                _album.value = data
                callback(data.id)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (e: Exception) {
                _eventNetworkError.value = true
            }
        }
    }

}