package com.proyectomovil.snorb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyectomovil.snorb.model.Publicacion

class ActivityPublicaciones : AppCompatActivity() {
    private lateinit var editTextTitulo: EditText
    private lateinit var editTextContenido: EditText
    private lateinit var buttonPublicar: Button
    private lateinit var listViewPublicaciones: ListView
    private lateinit var publicacionAdapter: PublicacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicaciones)

        editTextTitulo = findViewById(R.id.editTextTitulo)
        editTextContenido = findViewById(R.id.editTextContenido)
        buttonPublicar = findViewById(R.id.buttonPublicar)
        listViewPublicaciones = findViewById(R.id.listViewPublicaciones)

        publicacionAdapter = PublicacionAdapter(this, mutableListOf())
        listViewPublicaciones.adapter = publicacionAdapter

        buttonPublicar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val contenido = editTextContenido.text.toString()
            if (titulo.isNotEmpty() && contenido.isNotEmpty()) {
                publicar(titulo, contenido)
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        cargarPublicaciones()
    }

    private fun publicar(titulo: String, contenido: String) {
        val database = FirebaseDatabase.getInstance().getReference("publicaciones")
        val publicacionId = database.push().key
        val publicacion = Publicacion(publicacionId, titulo, contenido)

        publicacionId?.let {
            database.child(it).setValue(publicacion).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Publicación realizada con éxito", Toast.LENGTH_SHORT).show()
                    editTextTitulo.text.clear()
                    editTextContenido.text.clear()
                    cargarPublicaciones()  // Actualiza la lista de publicaciones después de publicar
                } else {
                    Toast.makeText(this, "Error al realizar la publicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarPublicaciones() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("publicaciones")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val publicaciones = mutableListOf<Publicacion>()
                for (dataSnapshot in snapshot.children) {
                    val publicacion = dataSnapshot.getValue(Publicacion::class.java)
                    publicacion?.let { publicaciones.add(it) }
                }
                publicacionAdapter.clear()
                publicacionAdapter.addAll(publicaciones)
                publicacionAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores aquí
            }
        })
    }
}
