package com.diegulog.marvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.diegulog.marvel.adapters.CharacterLoadStateAdapter
import com.diegulog.marvel.adapters.CharactersAdapter
import com.diegulog.marvel.utils.LOADING_TYPE_ITEM
import com.diegulog.marvel.utils.toVisibility
import com.diegulog.marvel.viewmodels.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.network_state_item.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharacterFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character, container, false)
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
        retry_button.setOnClickListener{
            charactersAdapter.retry()
        }

        recycler_view.apply {
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
                progress_bar.toVisibility(loadStates.refresh is LoadState.Loading)
                retry_button.toVisibility(loadStates.refresh is LoadState.Error)
                error_msg.toVisibility(loadStates.refresh is LoadState.Error)
            }
        }
    }
}