package com.example.vinyls.model

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.model.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<AlbumDBDao>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbums({
            //Guardar los coleccionistas de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }

    fun createAlbum(callback: (AlbumDBDao)->Unit, onError: (VolleyError)->Unit, body: JSONObject) {
        NetworkServiceAdapter.getInstance(application).postAlbum({
            callback(it)
        },
            onError,
            body
        )
    }
}