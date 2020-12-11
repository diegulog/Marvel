package com.diegulog.marvel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.diegulog.marvel.adapters.CharacterLoadStateAdapter
import com.diegulog.marvel.adapters.CharactersAdapter
import com.diegulog.marvel.databinding.FragmentCharacterBinding
import com.diegulog.marvel.utils.LOADING_TYPE_ITEM
import com.diegulog.marvel.utils.toVisibility
import com.diegulog.marvel.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupLoadingState()
    }

    private fun setupAdapter() {
        charactersAdapter = CharactersAdapter {

            val direction = CharacterFragmentDirections.actionFirstFragmentToSecondFragment(
                characterId = it.id
            )
            findNavController().navigate(direction)
        }

        val gridLayoutManager = GridLayoutManager(activity, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = charactersAdapter.getItemViewType(position)
                    return if (viewType == LOADING_TYPE_ITEM) 2
                    else 1
                }
            }
        }
        binding.networkStateItem.retryButton.setOnClickListener{
            charactersAdapter.retry()
        }

        binding.recyclerView.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = charactersAdapter.withLoadStateFooter(
                footer = CharacterLoadStateAdapter {
                    charactersAdapter.retry()
                }
            )
        }

        lifecycleScope.launch {
            viewModel.currentResult.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
    }

    private fun setupLoadingState() {
        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.networkStateItem.progressBar.toVisibility(loadStates.refresh is LoadState.Loading)
                binding.networkStateItem.retryButton.toVisibility(loadStates.refresh is LoadState.Error)
                binding.networkStateItem.errorMsg.toVisibility(loadStates.refresh is LoadState.Error)
            }
        }
    }
}