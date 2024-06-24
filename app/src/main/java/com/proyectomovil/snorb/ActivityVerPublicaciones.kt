package com.proyectomovil.snorb

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyectomovil.snorb.model.Publicacion

class ActivityVerPublicaciones : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var publicacionAdapter: PublicacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_publicaciones)

        listView = findViewById(R.id.listViewPublicaciones)
        publicacionAdapter = PublicacionAdapter(this, mutableListOf())
        listView.adapter = publicacionAdapter

        cargarPublicaciones()
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
