package com.jqlee.recipe.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jqlee.recipe.R
import com.jqlee.recipe.fragments.recipe.RecipeFragmentDirections
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        view.btnEdit.setOnClickListener{

        }

        return view
    }
}