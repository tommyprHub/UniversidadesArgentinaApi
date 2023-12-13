package com.tommy.universidadesargentinaapi

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tommy.universidadesargentinaapi.databinding.ItemUniBinding

class UniViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemUniBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(universidad: Universidad) {
        val pagWebText = if (universidad.webPages.isNullOrEmpty()) "N/A" else formatList(universidad.webPages)
        val dominioText = if (universidad.domains.isNullOrEmpty()) "N/A" else formatList(universidad.domains)

        binding.txUni.text = "Nombre: ${universidad.name}\n" +
                "Dominios: $dominioText"
    }

    private fun formatList(list: List<String>): String {
        return list.joinToString(", ") { it }
    }
}