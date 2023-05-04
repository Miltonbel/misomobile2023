package com.example.vinyls.album

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.vinyls.R
import com.example.vinyls.databinding.FragmentAddAlbumBinding
import org.json.JSONObject
import java.util.Calendar

class AddAlbumFragment : Fragment() {

    private var _binding: FragmentAddAlbumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.newAlbumRelease.setOnClickListener {
            createDatePicker()
        }

        binding.newAlbumButton.setOnClickListener {
            createAlbumAction()
        }

        //val textView: TextView = binding.textAddAlbum
        /*addAlbumViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
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

    private fun createAlbumAction(){

        val newAlbum = JSONObject(mapOf("name" to binding.newAlbumName.text.toString(),
            "cover" to binding.newAlbumCover.text.toString(),
            "releaseDate" to binding.newAlbumRelease.text.toString(),
            "description" to binding.newAlbumDescription.text.toString(),
            "genre" to binding.newAlbumGenre.text.toString(),
            "recordLabel" to binding.newAlbumLabel.text.toString()))

        val addAlbumViewModel = ViewModelProvider(this).get(AddAlbumViewModel::class.java)

        addAlbumViewModel.createNewAlbum(newAlbum)
    }

}