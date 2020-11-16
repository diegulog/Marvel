package com.diegulog.marvel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diegulog.marvel.R
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.utils.CHARACTERS_TYPE_ITEM
import com.diegulog.marvel.utils.LOADING_TYPE_ITEM

class CharactersAdapter(private val clickListener:(CharacterResponse) -> Unit) : PagingDataAdapter<CharacterResponse, CharactersAdapter.CharacterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
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

    class CharacterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.name)
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)

        fun bind(item: CharacterResponse) {

            name.text = item.name
            Glide.with(itemView.context)
                .load(item.thumbnailUrl("standard_xlarge"))
                .fitCenter()
                .placeholder(R.drawable.ic_image)
                .into(thumbnail)
        }

        companion object {
            fun create( parent: ViewGroup): CharacterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_character, parent, false)
                return CharacterViewHolder(view)
            }
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
