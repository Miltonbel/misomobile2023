package com.example.vinyls.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddAlbumViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is add album Fragment"
    }
    val text: LiveData<String> = _text
}