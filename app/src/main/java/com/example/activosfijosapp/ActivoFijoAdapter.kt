package com.example.activosfijosapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.activosfijosapp.databinding.ItemActivoFijoBinding

class ActivoFijoAdapter(
    private var activosFijos: ArrayList<ActivoFijo>,
    private val onClick: (ActivoFijo, View) -> Unit
) : RecyclerView.Adapter<ActivoFijoAdapter.ActivoFijoViewHolder>() {

    inner class ActivoFijoViewHolder(val binding: ItemActivoFijoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(activoFijo: ActivoFijo) {
            binding.tvNombre.text = activoFijo.nombre
            binding.tvDescripcion.text = activoFijo.descripcion
            binding.tvValor.text = activoFijo.valor.toString()

            itemView.setOnClickListener { onClick(activoFijo, it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivoFijoViewHolder {
        val binding = ItemActivoFijoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivoFijoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivoFijoViewHolder, position: Int) {
        holder.bind(activosFijos[position])
    }

    override fun getItemCount(): Int = activosFijos.size

    fun updateActivosFijos(nuevosActivosFijos: ArrayList<ActivoFijo>) {
        activosFijos.clear()
        activosFijos.addAll(nuevosActivosFijos)
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }
}
