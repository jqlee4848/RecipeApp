package com.jqlee.recipe.repository

import androidx.lifecycle.LiveData
import com.jqlee.recipe.data.RecipeDao
import com.jqlee.recipe.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {

    val readAllData: LiveData<List<Recipe>> = recipeDao.readAllData()

    suspend fun addRecipe(recipe: Recipe){
        recipeDao.addRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe){
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe){
        recipeDao.deleteRecipe(recipe)
    }
}