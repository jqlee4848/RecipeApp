package com.jqlee.recipe

data class Category(val img: Int, val name: String)

object Categories {
    private val images = intArrayOf(
            R.drawable.cat_img,
            R.drawable.soup_img,
            R.drawable.chicken_img,
            R.drawable.beef_img,
            R.drawable.dessert_img,
    )

    public val categories = arrayOf(
            "All Recipes",
            "Soup",
            "Chicken",
            "Beef",
            "Dessert"
    )

    var list: ArrayList<Category>? = null
        get() {
            if (field != null)
                return field

            field = ArrayList()
            for (i in images.indices) {
                val imageId = images[i]
                val categoryName = categories[i]

                val category = Category(imageId, categoryName)

                field!!.add(category)
            }
            return field
        }
}