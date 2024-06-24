package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyectomovil.snorb.R
import com.proyectomovil.snorb.Usuario

class ActivityListaUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_usuarios)

        val lv_usuarios = findViewById<ListView>(R.id.lvUsuarios)
        val list_usuarios: ArrayList<String> = ArrayList()
        val database = FirebaseDatabase.getInstance()
        val usuariosRef = database.getReference("Usuario")

        val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_usuarios)
        lv_usuarios.adapter = adaptador

        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adaptador.clear()
                for (registro in snapshot.children) {
                    val usuario = registro.getValue(Usuario::class.java)
                    val textoUsuario = "nombre: " + usuario?.nombre + ", direccion: " + usuario?.direccion
                    list_usuarios.add(textoUsuario)
                }
                adaptador.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al consultar los usuarios: ${error.message}")
            }
        })
    }
}
