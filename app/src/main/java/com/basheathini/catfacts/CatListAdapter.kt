package com.basheathini.catfacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatListAdapter internal constructor(
    context: Context) : RecyclerView.Adapter<CatListAdapter.CatViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cats = emptyList<Cat>()

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val current = cats[position]
        holder.catItemView.text = current.cat
    }

    internal fun setCats(cats: List<Cat>) {
        this.cats = cats
        notifyDataSetChanged()
    }

    override fun getItemCount() = cats.size
}