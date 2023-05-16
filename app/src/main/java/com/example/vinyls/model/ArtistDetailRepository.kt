package com.example.vinyls.model

import android.app.Application
import com.example.vinyls.model.network.NetworkServiceAdapter

class ArtistDetailRepository (val application: Application){
    suspend fun refreshData(artistId: Int): List<ArtistDetail> {
        return NetworkServiceAdapter.getInstance(application).getArtistDetail(artistId)
    }
}