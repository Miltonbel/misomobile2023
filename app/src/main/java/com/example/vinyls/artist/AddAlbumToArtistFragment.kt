package com.example.vinyls.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.databinding.FragmentAddAlbumToArtistBinding
import com.example.vinyls.model.AddAlbumToArtistAdapter


class AddAlbumToArtistFragment : Fragment() {

    private var _binding: FragmentAddAlbumToArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AddAlbumToArtistViewModel
    private var viewModelAdapter: AddAlbumToArtistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumToArtistBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModelAdapter = AddAlbumToArtistAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerAlbumToArtists
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(this, AddAlbumToArtistViewModel.Factory(activity.application))[AddAlbumToArtistViewModel::class.java]

        viewModel.artists.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.artist = this
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

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}