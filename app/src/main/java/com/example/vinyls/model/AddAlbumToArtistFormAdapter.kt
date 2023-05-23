package com.example.vinyls.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.artist.AddAlbumToArtistFormFragmentDirections
import com.example.vinyls.databinding.AlbumItemFormBinding
import com.squareup.picasso.Picasso

class AddAlbumToArtistFormAdapter :
    RecyclerView.Adapter<AddAlbumToArtistFormAdapter.AddAlbumTracksViewHolder>() {

    var album: List<AlbumDBDao> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var artistId: String = ""
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAlbumTracksViewHolder {
        val withDataBinding: AlbumItemFormBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AddAlbumTracksViewHolder.LAYOUT,
            parent,
            false
        )
        return AddAlbumTracksViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return album.size
    }
    private val _customlistener = MutableLiveData<String>()
    val customListener: LiveData<String> get() = _customlistener

    override fun onBindViewHolder(holder: AddAlbumTracksViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albumTrackFormText.text = album[position].name
            Picasso.get().load(album[position].cover).resize(450, 450)
                .into(it.albumTrackFormImage)
            it.albumTrackFormImage.clipToOutline = true

            holder.viewDataBinding.root.setOnClickListener {
                AddAlbumToArtistFormFragmentDirections.actionAddAlbumsToArtistFormToArtistDetailFragment(album[position].id)
                artistId
                _customlistener.value = album[position].id.toString()+","+artistId
            }
        }
    }

    class AddAlbumTracksViewHolder(val viewDataBinding: AlbumItemFormBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item_form
        }
    }
}