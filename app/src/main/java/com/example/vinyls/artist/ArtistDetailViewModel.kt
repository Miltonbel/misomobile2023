package com.example.vinyls.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls.model.ArtistDetail
import com.example.vinyls.model.ArtistDetailRepository

class ArtistDetailViewModel(application: Application, artistId: Int) :  AndroidViewModel(application) {

    private val _artist = MutableLiveData<List<ArtistDetail>>()

    val artistDetail: LiveData<List<ArtistDetail>>
        get() = _artist

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val artistDetailRepository = ArtistDetailRepository(application)

    val id: Int = artistId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        artistDetailRepository.refreshData(id,{
            _artist.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val artistId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app, artistId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}