package com.example.vinyls.model

import android.app.Application
import com.example.vinyls.model.network.NetworkServiceAdapter

class AlbumDetailRepository (val application: Application){
    suspend fun refreshData(albumId: Int): List<AlbumDetail> {
        return NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId)
    }
}