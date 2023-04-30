package com.example.vinyls.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinyls.databinding.FragmentAddAlbumTracksBinding

class AddAlbumTracksFragment : Fragment() {

    private var _binding: FragmentAddAlbumTracksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addAlbumTracksViewModel =
            ViewModelProvider(this).get(AddAlbumTracksViewModel::class.java)

        _binding = FragmentAddAlbumTracksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAddAlbumTracks
        addAlbumTracksViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}