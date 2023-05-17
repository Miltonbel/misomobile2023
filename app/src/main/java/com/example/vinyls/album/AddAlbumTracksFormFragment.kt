package com.example.vinyls.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.artist.ArtistDetailFragmentArgs
import com.example.vinyls.databinding.AlbumDetailFragmentBinding
import com.example.vinyls.databinding.FragmentAddAlbumsTracksFormBinding
import com.example.vinyls.model.AlbumDetail
import com.example.vinyls.model.AlbumDetailAdapter

class AddAlbumTracksFormFragment: Fragment() {

    private var _binding: FragmentAddAlbumsTracksFormBinding? = null
    private val binding get() = _binding!!
    //private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AddAlbumTracksFormViewModel
    //private var viewModelAdapter: AlbumDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumsTracksFormBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.newMinuteTrack.maxValue = 59
        binding.newSecondTrack.maxValue = 50


        //viewModelAdapter = AlbumDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //recyclerView = binding.albumDetailRv
        //recyclerView.layoutManager = LinearLayoutManager(context)
        //recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: AddAlbumTracksFormFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this, AddAlbumTracksFormViewModel.Factory(activity.application, args.albumId)).get(
            AddAlbumTracksFormViewModel::class.java)

    }


}