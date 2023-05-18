package com.example.vinyls.model

import android.app.Application
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class ArtistRepository (val application: Application){
    suspend fun refreshData(): List<Artist> {
        return NetworkServiceAdapter.getInstance(application).getArtists()
    }

    suspend fun addAlbumToArtist(albumId:Int,body: JSONObject){
        NetworkServiceAdapter.getInstance(application).postTracksToAlbum(albumId, body)
    }
}