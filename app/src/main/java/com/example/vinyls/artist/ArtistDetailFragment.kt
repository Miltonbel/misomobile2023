package com.example.vinyls.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.album.ArtistDetailViewModel
import com.example.vinyls.databinding.ArtistDetailFragmentBinding
import com.example.vinyls.model.ArtistDetail
import com.example.vinyls.model.ArtistDetailAdapter

class ArtistDetailFragment : Fragment() {

    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistDetailViewModel
    private var viewModelAdapter: ArtistDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModelAdapter = ArtistDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistDetailRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: ArtistDetailFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application, args.artistId)).get(
            ArtistDetailViewModel::class.java)
        viewModel.artistDetail.observe(viewLifecycleOwner, Observer<List<ArtistDetail>> {
            it.apply {
                viewModelAdapter!!.artist = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
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
