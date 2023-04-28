package com.example.vinyls.artist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinyls.album.AddAlbumViewModel
import com.example.vinyls.databinding.FragmentAddAlbumBinding
import com.example.vinyls.databinding.FragmentAddArtistBinding

class AddArtistFragment : Fragment() {


    private var _binding: FragmentAddArtistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addArtistViewModel =
            ViewModelProvider(this).get(AddArtistViewModel::class.java)

        _binding = FragmentAddArtistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAddArtist
        addArtistViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}