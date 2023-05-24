package com.example.vinyls.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinyls.R
import com.example.vinyls.artist.AddAlbumToArtistFragmentDirections
import com.example.vinyls.databinding.ArtistItemFormBinding

class AddAlbumToArtistAdapter :
    RecyclerView.Adapter<AddAlbumToArtistAdapter.AddAlbumToArtistViewHolder>() {

    var artist: List<Artist> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAlbumToArtistViewHolder {
        val withDataBinding: ArtistItemFormBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AddAlbumToArtistViewHolder.LAYOUT,
            parent,
            false
        )
        return AddAlbumToArtistViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return artist.size
    }

    override fun onBindViewHolder(holder: AddAlbumToArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albumToArtistFormText.text = artist[position].name
            holder.bind(artist[position])
            it.albumToArtistFormImage.clipToOutline = true
            it.artistDetail = artist[position]
            holder.viewDataBinding.root.setOnClickListener {
                val action = AddAlbumToArtistFragmentDirections.actionNavAddAlbumToArtistToAddAlbumsToArtistForm(artist[position].id.toString())
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }
    }

    class AddAlbumToArtistViewHolder(val viewDataBinding: ArtistItemFormBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item_form
        }
        fun bind(artist: Artist) {
            Glide.with(itemView)
                .load(artist.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .override(450, 450)
                        .error(R.drawable.ic_broken_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewDataBinding.albumToArtistFormImage)
        }
    }
}