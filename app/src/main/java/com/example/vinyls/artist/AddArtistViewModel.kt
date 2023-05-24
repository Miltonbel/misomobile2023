package com.example.vinyls.artist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.Artist
import com.example.vinyls.model.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AddArtistViewModel(application: Application) : AndroidViewModel(application) {
    private val _text = MutableLiveData<String>().apply {
        value = "This is add artist Fragment"
    }
    val text: LiveData<String> = _text

    private val _artist = MutableLiveData<Artist?>()

    val artist: MutableLiveData<Artist?>
        get() = _artist


    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage



    private val artistRepository = ArtistRepository(application)
    init {}
    fun createNewArtist(body: JSONObject, callback: (Int)-> Unit){

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val data = withContext(Dispatchers.IO) {
                    artistRepository.createArtist(body)

                }
                _artist.value = data
                callback(data.id)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (e: Exception) {
                _eventNetworkError.value = true
                _errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }
}