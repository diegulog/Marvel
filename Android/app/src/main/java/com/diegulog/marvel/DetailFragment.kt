package com.diegulog.marvel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.databinding.FragmentDetailsBinding
import com.diegulog.marvel.utils.loadGlide
import com.diegulog.marvel.utils.toVisibility
import com.diegulog.marvel.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        viewModel.result.observe(viewLifecycleOwner){ character ->
            binding.progressBar.toVisibility(false)
            if(character!=null) {
                setupData(character)
            }else{
                binding.description.text = getString(R.string.network_error)
            }
        }
        viewModel.getCharacterById(args.characterId)
    }

    private fun setupData(character: CharacterResponse){
        binding.image.loadGlide(character.thumbnailUrl("landscape_incredible"))
        binding.name.text = character.name
        binding.description.text = if(character.description.isEmpty()) getString(R.string.description) else character.description
    }
}