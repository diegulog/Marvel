package com.diegulog.marvel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diegulog.marvel.databinding.NetworkStateItemBinding
import com.diegulog.marvel.utils.toVisibility

class CharacterLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CharacterLoadStateAdapter.CharacterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CharacterLoadStateViewHolder {
        return CharacterLoadStateViewHolder(NetworkStateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    class CharacterLoadStateViewHolder(val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {

       fun bind(loadState: LoadState, retry: () -> Unit){
           if (loadState is LoadState.Error) {
               binding.errorMsg.text = loadState.error.localizedMessage
           }
           binding.retryButton.toVisibility(loadState !is LoadState.Loading)
           binding.errorMsg.toVisibility(loadState !is LoadState.Loading)
           binding.progressBar.toVisibility(loadState is LoadState.Loading)
           binding.retryButton.setOnClickListener {
               retry.invoke()
           }
       }
    }
}
