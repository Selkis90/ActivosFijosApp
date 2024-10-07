package com.example.activosfijosapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.example.activosfijosapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ActivoFijoAdapter
    private var activosFijos: ArrayList<ActivoFijo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        // Inicializa el adaptador
        adapter = ActivoFijoAdapter(activosFijos) { activoFijo, view ->
            mostrarActivoSeleccionado(activoFijo)
            // Mostrar el menú emergente al hacer clic en el elemento
            showPopupMenu(view, activoFijo)
        }

        binding.recyclerViewActivos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewActivos.adapter = adapter

        binding.btnCrear.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val descripcion = binding.etDescripcion.text.toString()
            val valor = binding.etValor.text.toString()

            if (nombre.isEmpty() || descripcion.isEmpty() || valor.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val activoFijo = ActivoFijo(nombre = nombre, descripcion = descripcion, valor = valor.toDoubleOrNull() ?: 0.0)
            db.collection("activos_fijos").add(activoFijo)
                .addOnSuccessListener {
                    Toast.makeText(this, "Activo creado exitosamente", Toast.LENGTH_SHORT).show()
                    cargarActivos()
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error al crear activo: ${e.message}")
                    Toast.makeText(this, "Error al crear activo", Toast.LENGTH_SHORT).show()
                }
        }

        cargarActivos()
    }

    private fun cargarActivos() {
        db.collection("activos_fijos").get().addOnSuccessListener { result ->
            activosFijos.clear()
            for (document in result) {
                val activo = document.toObject(ActivoFijo::class.java).copy(id = document.id) // Obtener el ID del documento
                activosFijos.add(activo)
            }
            adapter.updateActivosFijos(activosFijos) // Actualiza la lista del adaptador
        }.addOnFailureListener { e ->
            Log.e("FirestoreError", "Error al cargar activos: ${e.message}")
            Toast.makeText(this, "Error al cargar activos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarActivoSeleccionado(activoFijo: ActivoFijo) {
        binding.etId.setText(activoFijo.id)
        binding.etNombre.setText(activoFijo.nombre)
        binding.etDescripcion.setText(activoFijo.descripcion)
        binding.etValor.setText(activoFijo.valor.toString())
    }

    // Método para mostrar el menú emergente
    private fun showPopupMenu(view: View, activoFijo: ActivoFijo) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_edit -> {
                    editarActivo(activoFijo)
                    true
                }
                R.id.item_delete -> {
                    eliminarActivo(activoFijo)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }



    private fun editarActivo(activoFijo: ActivoFijo) {
        // Implementa la lógica para editar el activo fijo
        // Por ejemplo, podrías llenar los campos de texto con los datos del activo fijo
        mostrarActivoSeleccionado(activoFijo)
        Toast.makeText(this, "Editando activo: ${activoFijo.nombre}", Toast.LENGTH_SHORT).show()
        // Aquí puedes abrir una nueva actividad o un diálogo para editar los datos
    }

    private fun eliminarActivo(activoFijo: ActivoFijo) {
        // Lógica para eliminar el activo fijo de Firestore
        db.collection("activos_fijos").document(activoFijo.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Activo eliminado exitosamente", Toast.LENGTH_SHORT).show()
                cargarActivos() // Recargar los activos después de eliminar
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error al eliminar activo: ${e.message}")
                Toast.makeText(this, "Error al eliminar activo", Toast.LENGTH_SHORT).show()
            }
    }
}
