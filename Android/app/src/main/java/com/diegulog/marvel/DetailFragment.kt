package com.diegulog.marvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.utils.toVisibility
import com.diegulog.marvel.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        viewModel.result.observe(viewLifecycleOwner){ character ->
            progressBar.toVisibility(false)

            if(character!=null) {
                setupData(character)
            }else{
                description.text = getString(R.string.network_error)
            }
        }
        viewModel.getCharacterById(args.characterId)
    }

    private fun setupData(character: CharacterResponse){
        Glide.with(this)
            .load(character.thumbnailUrl("landscape_incredible"))
            .centerCrop()
            .placeholder(R.drawable.ic_image)

            .into(image)
        name.text = character.name
        description.text = if(character.description.isEmpty()) getString(R.string.description) else character.description
    }
}