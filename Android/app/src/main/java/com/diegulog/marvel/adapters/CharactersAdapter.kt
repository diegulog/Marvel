package com.diegulog.marvel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.databinding.ListItemCharacterBinding
import com.diegulog.marvel.utils.CHARACTERS_TYPE_ITEM
import com.diegulog.marvel.utils.LOADING_TYPE_ITEM
import com.diegulog.marvel.utils.loadGlide

class CharactersAdapter(private val clickListener:(CharacterResponse) -> Unit) : PagingDataAdapter<CharacterResponse, CharactersAdapter.CharacterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener {
                clickListener(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount){
            LOADING_TYPE_ITEM
        }else {
            CHARACTERS_TYPE_ITEM
        }
    }

    class CharacterViewHolder(private val binding: ListItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterResponse) {
            binding.name.text = item.name
            binding.thumbnail.loadGlide(item.thumbnailUrl("standard_xlarge"))
        }

    }
}

private class DiffCallback : DiffUtil.ItemCallback<CharacterResponse>() {
    override fun areItemsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean {
        return oldItem == newItem
    }
}
