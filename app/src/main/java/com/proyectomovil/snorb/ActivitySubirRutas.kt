package com.proyectomovil.snorb

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class ActivitySubirRutas : AppCompatActivity() {

    private lateinit var btnSelectFile: Button
    private lateinit var btnUploadFile: Button
    private lateinit var imageViewPreview: ImageView
    private var fileUri: Uri? = null
    private val storageReference: StorageReference by lazy {
        FirebaseStorage.getInstance().reference.child("imagenes")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subir_rutas)

        btnSelectFile = findViewById(R.id.btnSelectFile)
        btnUploadFile = findViewById(R.id.btnUploadFile)
        imageViewPreview = findViewById(R.id.imageViewPreview)

        btnSelectFile.setOnClickListener {
            selectFile()
        }

        btnUploadFile.setOnClickListener {
            uploadFile()
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            fileUri = data?.data
            btnUploadFile.isEnabled = fileUri != null

            if (fileUri != null) {
                imageViewPreview.visibility = View.VISIBLE
                Glide.with(this).load(fileUri).into(imageViewPreview)
            } else {
                imageViewPreview.visibility = View.GONE
            }
        }
    }

    private fun uploadFile() {
        fileUri?.let {
            val fileName = UUID.randomUUID().toString()
            val fileRef = storageReference.child(fileName)
            fileRef.putFile(it)
                .addOnSuccessListener {
                    Toast.makeText(this, "File uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to upload file: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
