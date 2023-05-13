package com.example.vinyls.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.util.forEach
import androidx.navigation.fragment.findNavController
import com.example.vinyls.R
import com.example.vinyls.databinding.FragmentAddAlbumBinding
import org.json.JSONObject

class AddAlbumFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAddAlbumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var genreSelected: String? = null
    private var labelSelected: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sparseArray: SparseArray<Spinner> = SparseArray()
        sparseArray.set(R.array.genres, binding.spinnerGenre)
        sparseArray.set(R.array.labels, binding.spinnerLabel)
        createSpinner(sparseArray)

        binding.newAlbumRelease.setOnClickListener {
            createDatePicker()
        }
        binding.newAlbumButton.setOnClickListener {
            if(checkValidForm()){
                createAlbumAction({ newAlbumId: Int ->
                    findNavController().navigate(
                        AddAlbumFragmentDirections.actionNavAddAlbumToAlbumDetailFragment(newAlbumId)
                    )
                },{})
                clearForm()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun createDatePicker(){
        val datePickerFragment = DatePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager

        // we have to implement setFragmentResultListener
        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                val date = bundle.getString("SELECTED_DATE")
                binding.newAlbumRelease.setText(date)
            }
        }

        // show
        datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
    }

    private fun createSpinner(spinners: SparseArray<Spinner>){
        spinners.forEach { arrayId, spinner ->
            ArrayAdapter.createFromResource(
                this.requireContext(),
                arrayId,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
            spinner.onItemSelectedListener = this
        }
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val itemAtPosition = parent.getItemAtPosition(pos)
        when(parent.id){
            R.id.spinner_genre -> genreSelected = itemAtPosition.toString()
            R.id.spinner_label -> labelSelected = itemAtPosition.toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun checkValidForm(): Boolean {
        var isValid = true
        if(binding.newAlbumName.text.toString().isEmpty()){
            binding.newAlbumName.error = "Ingrese el nombre del album"
            isValid = false
        }
        if(binding.newAlbumCover.text.toString().isEmpty()){
            binding.newAlbumCover.error = "Ingrese la imagen de la portada del album"
            isValid = false
        }
        if(binding.newAlbumRelease.text.toString().isEmpty()){
            binding.newAlbumRelease.error = "Ingrese la fecha de lanzamiento del album"
            isValid = false
        }
        if(binding.newAlbumDescription.text.toString().isEmpty()){
            binding.newAlbumDescription.error = "ingrese una descripción para el album"
            isValid = false
        }

        return isValid
    }

    private fun createAlbumAction(navigate: (Int) -> Unit, error:()->Unit){
        val newAlbum = buildCreateBody()
        val addAlbumViewModel = ViewModelProvider(this).get(AddAlbumViewModel::class.java)

        addAlbumViewModel.createNewAlbum(newAlbum, navigate)
    }


    private fun buildCreateBody():JSONObject{
        return JSONObject(mapOf(
            "name" to binding.newAlbumName.text.toString(),
            "cover" to binding.newAlbumCover.text.toString(),
            "releaseDate" to binding.newAlbumRelease.text.toString(),
            "description" to binding.newAlbumDescription.text.toString(),
            "genre" to genreSelected,
            "recordLabel" to labelSelected))
    }

    private fun clearForm(){
        binding.newAlbumName.setText("")
        binding.newAlbumRelease.setText("")
        binding.newAlbumCover.setText("")
        binding.newAlbumDescription.setText("")
    }

}