package com.example.demotest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demotest.databinding.ItemHighlightBinding


class HighlightAdapter(
    private var list: List<String>,
) : RecyclerView.Adapter<HighlightAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHighlightBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHighlightBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.textViewHighlight.text = this
            }
        }
    }

    override fun getItemCount() = list.size
}