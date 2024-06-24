package com.proyectomovil.snorb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

// Definir constantes para los argumentos del fragmento
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AjustesCiudadano : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ajustes_ciudadano, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val perf = view.findViewById<Button>(R.id.btnPerfil)
        perf.setOnClickListener {
            val intent = Intent(requireContext(), ActivityPerfil::class.java)
            startActivity(intent)
        }

        val rep = view.findViewById<Button>(R.id.btnReportes)
        rep.setOnClickListener {
            val intent = Intent(requireContext(), ActivityReportesCiudadano::class.java)
            startActivity(intent)
        }

        val pub = view.findViewById<Button>(R.id.btnVerPublicaciones)
        pub.setOnClickListener {
            val intent = Intent(requireContext(), ActivityVerPublicaciones::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AjustesCiudadano().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
