package com.example.vinyls.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vinyls.album.DatePickerFragment
import com.example.vinyls.databinding.FragmentAddArtistBinding
import org.json.JSONObject

class AddArtistFragment : Fragment() {


    private var _binding: FragmentAddArtistBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddArtistBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.newArtistBirthDate.setOnClickListener {
            createDatePicker()
        }
        binding.newArtistButton.setOnClickListener {
            if (checkValidForm()) {
                createArtistAction({ newArtistId: Int ->
                    findNavController().navigate(
                        AddArtistFragmentDirections.actionNavAddArtistToArtistDetailFragment(newArtistId)
                    )
                    Toast.makeText(requireContext(), "Artist created successfully", Toast.LENGTH_SHORT).show()
                }, {})
                clearForm()
            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createDatePicker() {
        val datePickerFragment = DatePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager

        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                val date = bundle.getString("SELECTED_DATE")
                binding.newArtistBirthDate.setText(date)
            }
        }
        datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
    }

    private fun checkValidForm(): Boolean {
        var isValid = true
        if (binding.newArtistName.text.toString().isEmpty()) {
            binding.newArtistName.error = "Ingrese el nombre del artista"
            isValid = false
        }
        if (binding.newArtistImage.text.toString().isEmpty()) {
            binding.newArtistImage.error = "Ingrese la imagen de la portada del artista"
            isValid = false
        }
        if (binding.newArtistBirthDate.text.toString().isEmpty()) {
            binding.newArtistBirthDate.error = "Ingrese la fecha de nacimiento del artista"
            isValid = false
        }
        if (binding.newArtistDescription.text.toString().isEmpty()) {
            binding.newArtistDescription.error = "ingrese una descripción para el artista"
            isValid = false
        }

        return isValid
    }

    private fun createArtistAction(navigate: (Int) -> Unit, error: () -> Unit) {
        val newArtist = buildCreateBody()
        val addArtistViewModel = ViewModelProvider(this).get(AddArtistViewModel::class.java)

        addArtistViewModel.createNewArtist(newArtist, navigate)
        addArtistViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildCreateBody(): JSONObject {
        return JSONObject(
            mapOf(
                "name" to binding.newArtistName.text.toString(),
                "image" to binding.newArtistImage.text.toString(),
                "birthDate" to binding.newArtistBirthDate.text.toString(),
                "description" to binding.newArtistDescription.text.toString()
            )
        )
    }
    private fun clearForm() {
        binding.newArtistName.setText("")
        binding.newArtistBirthDate.setText("")
        binding.newArtistImage.setText("")
        binding.newArtistDescription.setText("")
    }
}