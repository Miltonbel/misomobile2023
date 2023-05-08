package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter

class ArtistRepository (val application: Application){
    fun refreshData(callback: (List<Artist>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getArtists({
            callback(it)
        },
            onError
        )
    }
}