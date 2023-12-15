package com.tommy.universidadesargentinaapi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UniAdapter(

    private val universidades:List<Universidad>,
    private val onItemClick: (Universidad) -> Unit
):RecyclerView.Adapter<UniViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = UniViewHolder(layoutInflater.inflate(R.layout.item_uni, parent, false))

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(universidades[position])

                val intent = Intent(it.context, DatosUniversidad::class.java)
                intent.putExtra("universidad", universidades[position])
                it.context.startActivity(intent)
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int = universidades.size
    override fun onBindViewHolder(holder: UniViewHolder, position: Int) {
        val item = universidades[position]
        holder.bind(item)
    }
}
