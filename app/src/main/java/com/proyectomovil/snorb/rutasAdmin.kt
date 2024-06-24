package com.proyectomovil.snorb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.proyectomovil.snorb.databinding.FragmentRutasAdminBinding

class rutasAdmin : Fragment() {

    private lateinit var binding: FragmentRutasAdminBinding
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    private val storageReference: StorageReference by lazy {
        FirebaseStorage.getInstance().reference.child("imagenes")
    }
    private val imageUrls: MutableList<String> = mutableListOf()
    private val imageNames: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using view binding
        binding = FragmentRutasAdminBinding.inflate(inflater, container, false)
        spinner = binding.spinner
        imageView = binding.imageView

        loadImagesFromFirebase()

        return binding.root
    }

    private fun loadImagesFromFirebase() {
        storageReference.listAll().addOnSuccessListener { listResult ->
            val items = listResult.items
            if (items.isNotEmpty()) {
                for (item in items) {
                    item.downloadUrl.addOnSuccessListener { uri ->
                        imageUrls.add(uri.toString())
                        imageNames.add(item.name)  // Extract the file name
                        if (imageUrls.size == items.size) {
                            setupSpinner()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "No images found in Firebase Storage", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to list images from Firebase Storage", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, imageNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val imageUrl = imageUrls[position]
                Glide.with(this@rutasAdmin)
                    .load(imageUrl)
                    .into(imageView)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}
