package com.tommy.universidadesargentinaapi

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tommy.universidadesargentinaapi.databinding.ItemUniBinding

class UniViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemUniBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(universidad: Universidad) {

        binding.txUni.text = "${universidad.name}\n" +
                "Estado: ${universidad.stateProvince}"
    }

    private fun formatList(list: List<String>): String {
        return list.joinToString(", ") { it }
    }
}