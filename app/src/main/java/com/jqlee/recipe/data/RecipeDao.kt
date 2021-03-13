package com.jqlee.recipe.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jqlee.recipe.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE category = '1'")
    fun readSoup(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE category = '2'")
    fun readChicken(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE category = '3'")
    fun readBeef(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE category = '4'")
    fun readDessert(): LiveData<List<Recipe>>
}