package com.jqlee.recipe.fragments.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jqlee.recipe.Categories
import com.jqlee.recipe.R
import com.jqlee.recipe.model.Recipe
import com.jqlee.recipe.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_recipe.view.*
import kotlin.properties.Delegates

class AddFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var type: String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        view.btnUpload.setOnClickListener{
            insertDataToDatabase()
        }

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Categories.categories)

        view.categoryType.adapter = arrayAdapter

        view.categoryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = Categories.categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                type = Categories.categories[0]
            }
        }

        view.btnPickImg.setOnClickListener {
            // check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val permissionCheck = checkSelfPermission(requireContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)

                if(permissionCheck == PackageManager.PERMISSION_DENIED){
                    // permission denied
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                    // show popup to request runtime permission
                    requestPermissions(permission, PERMISSION_CODE)

                }else{
                    // permission already granted
                    pickImgFromGallery()
                }
            }else{
                // OS version not compatible
            }

        }

        return view
    }

    private fun pickImgFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object{
        // image pick code
        private val IMAGE_PICK_CODE = 1000

        // permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permission from popup granted
                    pickImgFromGallery()
                }else{
                    // permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private lateinit var drawable: BitmapDrawable

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            imgView.setImageURI(data?.data)
            drawable = imgView.drawable as BitmapDrawable
        }
    }

    private fun insertDataToDatabase() {
        val recipeName = recipeName.text.toString()
        val ingredient = ingredientsText.text.toString()
        val step = stepText.text.toString()
        val img = drawable.bitmap
        val type = type

        if(inputCheck(recipeName, ingredient, step)){
            // Create recipe object
            val recipe = Recipe(0, recipeName, type, img, ingredient, step)

            // Add data to database
            recipeViewModel.addRecipe(recipe)

            Toast.makeText(requireContext(), "Successfully uploaded!", Toast.LENGTH_LONG).show()

            // Navigate back to recipe list
            findNavController().navigate(R.id.action_addFragment_to_recipeFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill up all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, ingredient: String, step: String): Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(ingredient) && TextUtils.isEmpty(step))
    }

}