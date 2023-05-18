package com.example.vinyls.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinyls.databinding.FragmentAddAlbumsToArtistFormBinding
import org.json.JSONObject

class AddAlbumToArtistFormFragment: Fragment()  {

    private val MAX_TIME = 59
    private var _binding: FragmentAddAlbumsToArtistFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddAlbumToArtistFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumsToArtistFormBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.newMinuteTrack.maxValue = MAX_TIME
        binding.newSecondTrack.maxValue = MAX_TIME

        binding.newAlbumArtistButton.setOnClickListener {
            createAlbumTrackAction({
                findNavController().navigate(
                    AddAlbumToArtistFormFragmentDirections.actionAddAlbumsToArtistFormToArtistDetailFragment(it)
                )
            }) {}
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: AddAlbumToArtistFormFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this, AddAlbumToArtistFormViewModel.Factory(activity.application, args.artistId)).get(
            AddAlbumToArtistFormViewModel::class.java)
    }

    private fun createAlbumTrackAction(navigate: (Int) -> Unit, function: () -> Unit){
        val newAlbumArtist = buildCreateBody()
        val addAlbumArtistViewModel = ViewModelProvider(this)[AddAlbumToArtistFormViewModel::class.java]

        addAlbumArtistViewModel.addTrackToAlbum(newAlbumArtist, navigate)
    }

    private fun buildCreateBody(): JSONObject {
        return JSONObject(mapOf("name" to binding.newTrackName.text.toString(),
            "duration" to String.format("%02d:%02d", binding.newMinuteTrack.value, binding.newSecondTrack.value)
        ))
    }
}