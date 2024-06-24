package com.proyectomovil.snorb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.ActivityListaUsuarios // Asegúrate de que esta importación esté correcta
import com.proyectomovil.snorb.databinding.ActivitySubirRutasBinding

// Definir constantes para los argumentos del fragmento
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AjustesAdmin : Fragment() {
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
        return inflater.inflate(R.layout.fragment_ajustes_admin, container, false)
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
            val intent = Intent(requireContext(), ActivityReportesAdmin::class.java)
            startActivity(intent)
        }

        val usr = view.findViewById<Button>(R.id.btnListaUsuarios)
        usr.setOnClickListener {
            val intent = Intent(requireContext(), ActivityListaUsuarios::class.java)
            startActivity(intent)
        }

        val pub = view.findViewById<Button>(R.id.btnPublicaciones)
        pub.setOnClickListener {
            val intent = Intent(requireContext(), ActivityPublicaciones::class.java)
            startActivity(intent)
        }
        val rut = view.findViewById<Button>(R.id.btnRutas)
        rut.setOnClickListener{
            val intent =Intent(requireContext(), ActivitySubirRutas::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AjustesAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
