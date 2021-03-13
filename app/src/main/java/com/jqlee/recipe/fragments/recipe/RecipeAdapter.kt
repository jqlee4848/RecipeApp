package com.jqlee.recipe.fragments.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jqlee.recipe.R
import com.jqlee.recipe.model.Recipe
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeAdapter: RecyclerView.Adapter<RecipeAdapter.ItemViewHolder>() {

    private var recipeList = emptyList<Recipe>()
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = recipeList[position]
        holder.itemView.itemName.text = currentItem.name

        holder.itemView.rootItem.setOnClickListener{
            val action = RecipeFragmentDirections.actionRecipeFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun setData(recipe: List<Recipe>){
        this.recipeList = recipe
        notifyDataSetChanged()
    }
}