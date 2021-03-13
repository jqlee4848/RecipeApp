package com.jqlee.recipe.fragments.update

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jqlee.recipe.Categories
import com.jqlee.recipe.R
import com.jqlee.recipe.fragments.add.AddFragment
import com.jqlee.recipe.model.Recipe
import com.jqlee.recipe.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlin.properties.Delegates

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var category = 0

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var drawable: Bitmap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        view.updateName.setText(args.currentItem.name)
        view.updateIngredients.setText(args.currentItem.ingredient)
        view.updateStep.setText(args.currentItem.steps)
        view.img_view.setImageBitmap(args.currentItem.img)

        drawable = args.currentItem.img

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Categories.categories)

        view.updateType.adapter = arrayAdapter

        view.updateType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.updateType?.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                view.updateType.setSelection(checkType(args.currentItem.category))
            }
        }
        view.btnSave.setOnClickListener{
            updateItem()
        }

        view.btnDelete.setOnClickListener{
            deleteRecipe()
        }

        view.btnUpdateImg.setOnClickListener {
            // check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val permissionCheck = ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)

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

    companion object{
        // image pick code
        private val IMAGE_PICK_CODE = 1000

        // permission code
        private val PERMISSION_CODE = 1001
    }

    private fun checkType(type: String): Int {
        if(type == "All Recipes"){
            category = 0;
        }else if(type == "Soup"){
            category = 1;
        }else if(type == "Chicken"){
            category = 2;
        }else if(type == "Beef"){
            category = 3;
        }else if(type == "Dessert"){
            category = 4;
        }

        return category
    }

    private fun pickImgFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            img_view.setImageURI(data?.data)
            drawable = img_view.drawable as Bitmap
        }
    }

    private fun updateItem(){
        val name = updateName.text.toString()
        val ingredient = updateIngredients.text.toString()
        val step = updateStep.text.toString()
        val img = drawable
        val type = updateType.selectedItem.toString()

        if(inputCheck(name, ingredient, step)){

            // Create recipe object
            val updatedItem = Recipe(args.currentItem.id, name, type, img, ingredient, step)

            // Update current recipe
            recipeViewModel.updateRecipe(updatedItem)
            Toast.makeText(requireContext(), "Updated recipe successfully!", Toast.LENGTH_SHORT).show()

            // Navigate back to recipe list
            findNavController().navigate(R.id.action_updateFragment_to_recipeFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill up all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, ingredient: String, step: String): Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(ingredient) && TextUtils.isEmpty(step))
    }

    private fun deleteRecipe(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            recipeViewModel.deleteRecipe(args.currentItem)
            Toast.makeText(requireContext(), "Successfully removed ${args.currentItem.name}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_recipeFragment)
        }

        builder.setNegativeButton("No"){ _, _ -> }

        builder.setTitle("Delete ${args.currentItem.name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentItem.name}?")
        builder.create().show()
    }
}