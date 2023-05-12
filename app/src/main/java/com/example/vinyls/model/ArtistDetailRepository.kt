package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter

class ArtistDetailRepository (val application: Application){
    fun refreshData(artistId: Int, callback: (List<ArtistDetail>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getArtistDetail(artistId,{
            callback(it)
        },
            onError
        )
    }
}