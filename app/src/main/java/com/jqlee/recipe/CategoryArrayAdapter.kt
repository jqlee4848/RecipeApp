package com.jqlee.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CategoryArrayAdapter(context: Context, categoryList: List<Category>): ArrayAdapter<Category>(context, 0, categoryList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View{
        val category = getItem(position)

        val view = LayoutInflater.from(context).inflate(R.layout.recipetypes, parent, false)
        val img = view.findViewById<ImageView>(R.id.catImage)
        val name = view.findViewById<TextView>(R.id.tv_cat_name)

        img.setImageResource(category!!.img)
        name.text = category.name

        return view
    }
}