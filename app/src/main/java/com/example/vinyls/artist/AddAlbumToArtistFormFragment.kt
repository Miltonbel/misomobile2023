package com.example.vinyls.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.album.AddAlbumTracksViewModel
import com.example.vinyls.databinding.FragmentAddAlbumsToArtistFormBinding
import com.example.vinyls.model.AddAlbumTracksAdapter

class AddAlbumToArtistFormFragment: Fragment()  {

    private var _binding: FragmentAddAlbumsToArtistFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AddAlbumTracksViewModel
    private var viewModelAdapter: AddAlbumTracksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumsToArtistFormBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModelAdapter = AddAlbumTracksAdapter()

        binding.newAlbumTrackButton.setOnClickListener {
            createAlbumToArtistAction {
                findNavController().navigate(
                    AddAlbumToArtistFormFragmentDirections.actionAddAlbumsToArtistFormToArtistDetailFragment(
                        it
                    )
                )
            }
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

    private fun createAlbumToArtistAction(navigate: (Int) -> Unit){
        val addAlbumToArtistViewModel = ViewModelProvider(this)[AddAlbumToArtistFormViewModel::class.java]

        addAlbumToArtistViewModel.addAlbumToArtist(navigate)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}