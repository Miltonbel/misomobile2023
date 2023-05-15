package com.example.vinyls.artist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinyls.databinding.FragmentAddAlbumToArtistBinding

class AddAlbumToArtistFragment : Fragment() {

    private var _binding: FragmentAddAlbumToArtistBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addAlbumToArtistViewModel =
            ViewModelProvider(this).get(AddAlbumToArtistViewModel::class.java)

        _binding = FragmentAddAlbumToArtistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAddAlbumToArtist
        addAlbumToArtistViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}