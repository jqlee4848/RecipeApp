package com.jqlee.recipe.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,
    val category: String,
    val img: Bitmap,
    val ingredient: String,
    val steps: String
): Parcelable