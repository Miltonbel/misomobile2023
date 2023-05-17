package com.example.vinyls.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.album.AddAlbumTracksFragmentDirections
import com.example.vinyls.databinding.AlbumItemFormBinding
import com.squareup.picasso.Picasso

class AddAlbumTracksAdapter :
    RecyclerView.Adapter<AddAlbumTracksAdapter.AddAlbumTracksViewHolder>() {

    var album: List<AlbumDBDao> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAlbumTracksViewHolder {
        val withDataBinding: AlbumItemFormBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AddAlbumTracksAdapter.AddAlbumTracksViewHolder.LAYOUT,
            parent,
            false
        )
        return AddAlbumTracksAdapter.AddAlbumTracksViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return album.size
    }

    override fun onBindViewHolder(holder: AddAlbumTracksViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albumTrackFormText.text = album[position].name
            Picasso.get().load(album[position].cover).resize(450, 450)
                .into(it.albumTrackFormImage)
            it.albumTrackFormImage.clipToOutline = true

            holder.viewDataBinding.root.setOnClickListener {
                val action = AddAlbumTracksFragmentDirections.actionNavAddAlbumTracksToAddAlbumsTracksForm(album[position].id.toString())
                holder.viewDataBinding.root.findNavController().navigate(action)
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