package com.example.vinyls.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinyls.R
import com.example.vinyls.databinding.FragmentAddAlbumsTracksFormBinding
import org.json.JSONObject

class AddAlbumTracksFormFragment: Fragment()  {

    private val MAX_TIME = 59

    private var _binding: FragmentAddAlbumsTracksFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddAlbumTracksFormViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAlbumsTracksFormBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.newMinuteTrack.maxValue = MAX_TIME
        binding.newSecondTrack.maxValue = MAX_TIME

        binding.newAlbumTrackButton.setOnClickListener {
            createAlbumTrackAction({
                findNavController().navigate(
                    AddAlbumTracksFormFragmentDirections.actionAddAlbumsTracksFormToAlbumDetailFragment(it)
                )
            },{})
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: AddAlbumTracksFormFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this, AddAlbumTracksFormViewModel.Factory(activity.application, args.albumId)).get(
            AddAlbumTracksFormViewModel::class.java)

    }

    private fun createAlbumTrackAction(navigate: (Int) -> Unit, error:()->Unit){
        val newAlbumTrack = buildCreateBody()
        val addAlbumTrackViewModel = ViewModelProvider(this).get(AddAlbumTracksFormViewModel::class.java)

        addAlbumTrackViewModel.addTrackToAlbum(newAlbumTrack, navigate)
    }
    private fun buildCreateBody(): JSONObject {
        return JSONObject(mapOf("name" to binding.newTrackName.text.toString(),
            "duration" to String.format("%02d:%02d", binding.newMinuteTrack.value, binding.newSecondTrack.value)
        ))
    }



}