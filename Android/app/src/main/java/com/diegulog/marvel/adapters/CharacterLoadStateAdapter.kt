package com.diegulog.marvel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diegulog.marvel.R
import com.diegulog.marvel.utils.toVisibility
import kotlinx.android.synthetic.main.network_state_item.view.*

class CharacterLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CharacterLoadStateAdapter.CharacterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState) {
        val progress = holder.itemView.progress_bar
        val btnRetry = holder.itemView.retry_button
        val txtErrorMessage = holder.itemView.error_msg

        if (loadState is LoadState.Error) {
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        btnRetry.toVisibility(loadState !is LoadState.Loading)
        txtErrorMessage.toVisibility(loadState !is LoadState.Loading)
        progress.toVisibility(loadState is LoadState.Loading)

        btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CharacterLoadStateViewHolder {
        return CharacterLoadStateViewHolder.create(parent)
    }

    class CharacterLoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun create(parent: ViewGroup): CharacterLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.network_state_item, parent, false)
                return CharacterLoadStateViewHolder(view)
            }
        }
    }
}
