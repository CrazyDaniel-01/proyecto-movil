package com.proyectomovil.snorb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.proyectomovil.snorb.model.Publicacion

class PublicacionAdapter(context: Context, publicaciones: MutableList<Publicacion>) :
    ArrayAdapter<Publicacion>(context, 0, publicaciones) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_publicacion, parent, false)
        val publicacion = getItem(position)

        val tituloTextView = view.findViewById<TextView>(R.id.tituloTextView)
        val contenidoTextView = view.findViewById<TextView>(R.id.contenidoTextView)

        tituloTextView.text = publicacion?.titulo
        contenidoTextView.text = publicacion?.contenido

        return view
    }
}
