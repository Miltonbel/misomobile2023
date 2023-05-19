package com.example.vinyls.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.databinding.FragmentAddAlbumTracksBinding
import com.example.vinyls.model.AddAlbumTracksAdapter


class AddAlbumTracksFragment : Fragment() {

    private var _binding: FragmentAddAlbumTracksBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AddAlbumTracksViewModel
    private var viewModelAdapter: AddAlbumTracksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumTracksBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModelAdapter = AddAlbumTracksAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerAlbumTracks
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel =
            ViewModelProvider(this, AddAlbumTracksViewModel.Factory(activity.application)).get(
                AddAlbumTracksViewModel::class.java
            )

        viewModel.albums.observe(viewLifecycleOwner) {
            it.apply {
                viewModelAdapter!!.album = this
            }
        }
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

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