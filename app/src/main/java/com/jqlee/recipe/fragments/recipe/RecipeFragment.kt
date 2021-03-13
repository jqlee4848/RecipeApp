package com.jqlee.recipe.fragments.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jqlee.recipe.Categories
import com.jqlee.recipe.CategoryArrayAdapter
import com.jqlee.recipe.R
import com.jqlee.recipe.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipe.view.*

class RecipeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)

        setupSpinner(view.categorySpinner)

        view.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                view.tvText.text = Categories.categories[0]
            }
        }

        // Recipe item recycler view
        val adapter = RecipeAdapter()
        val rv = view.recipeRV
        rv.adapter  = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        // RecipeViewModel
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.readAllData.observe(viewLifecycleOwner, Observer { recipe ->
            adapter.setData(recipe)
        })

        view.addRecipeBtn.setOnClickListener{
            findNavController().navigate(R.id.action_recipeFragment_to_addFragment)
        }

        return view
    }

    private fun setupSpinner(spin: Spinner){
        val adapter = CategoryArrayAdapter(requireContext(), Categories.list!!)
        spin.adapter = adapter
    }
}