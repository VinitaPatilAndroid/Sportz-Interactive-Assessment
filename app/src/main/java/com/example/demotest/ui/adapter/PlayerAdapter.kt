package com.example.demotest.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demotest.data.model.Player
import com.example.demotest.databinding.ItemPlayerBinding

class PlayerAdapter(var items:MutableList<Player>  = mutableListOf(),
                    private val onPlayerSelection: (Player) -> Unit
) : RecyclerView.Adapter< PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onPlayerSelection(items[position])
                    }
                }
            }
        }

        fun bind(player: Player) {
            player.apply {
                binding.textViewPlayerName.text = player.nameFull
                if (isCaptain) {
                    binding.textViewPlayerName.text = player.nameFull
                    binding.textViewPlayerName.append("- Captain")
                } else if (iskeeper){
                    binding.textViewPlayerName.text = player.nameFull
                    binding.textViewPlayerName.append("- keeper")
                }
                else {
                    binding.textViewPlayerName.text = player.nameFull
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding =
            ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val currentItem = items.get(position)
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
       return items.size
    }
}