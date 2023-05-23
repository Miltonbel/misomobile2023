package com.example.vinyls.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.album.AddAlbumTracksViewModel
import com.example.vinyls.databinding.FragmentAddAlbumsToArtistFormBinding
import com.example.vinyls.model.AddAlbumToArtistFormAdapter

class AddAlbumToArtistFormFragment: Fragment()  {

    private var _binding: FragmentAddAlbumsToArtistFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AddAlbumTracksViewModel
    private var viewModelAdapter: AddAlbumToArtistFormAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumsToArtistFormBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModelAdapter = AddAlbumToArtistFormAdapter()
        viewModelAdapter!!.customListener.observe(viewLifecycleOwner) { message ->
            var tupleCreateNewAsociation = message.split(',')
            var albumId = tupleCreateNewAsociation[0]
            var artistId = tupleCreateNewAsociation[1]
            createAlbumToArtistAction(albumId,artistId,{
                findNavController().navigate(
                    AddAlbumToArtistFormFragmentDirections.actionAddAlbumsToArtistFormToArtistDetailFragment(
                        it
                    )
                )
            })
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
        binding.newAlbumTrackButton.setOnClickListener {

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerAlbumTracks
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AddAlbumTracksViewModel.Factory(activity.application)).get(
            AddAlbumTracksViewModel::class.java)

        viewModel.albums.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.album = this
                val args: AddAlbumToArtistFormFragmentArgs by navArgs()
                var temp = args.artistId
                viewModelAdapter!!.artistId = temp
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createAlbumToArtistAction(albumId:String,artistId:String, navigate: (Int) -> Unit){
//        val args: AddAlbumToArtistFormFragmentArgs by navArgs()
//        var temp = args.artistId
//        val addAlbumToArtistViewModel = ViewModelProvider(this)[AddAlbumToArtistFormViewModel::class.java]
//        viewModelAdapter?.artistId.let { temp }
//        viewModelAdapter?.album?.let { addAlbumToArtistViewModel.addAlbumToArtist(navigate, it[0]) }
          println("Works")
          val addAlbumToArtistViewModel = ViewModelProvider(this)[AddAlbumToArtistFormViewModel::class.java]
          addAlbumToArtistViewModel.addAlbumToArtist(navigate,albumId, artistId )
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}