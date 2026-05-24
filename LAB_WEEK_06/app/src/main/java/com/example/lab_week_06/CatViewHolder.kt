package com.example.lab_week_06

import android.content.DialogInterface.OnClickListener
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.lab_week_06.model.CatBreed
import com.example.lab_week_06.model.CatModel
import com.example.lab_week_06.model.Gender
import kotlin.getValue
import android.widget.ImageView

private val FEMALE_SYMBOL = "\u2640"
private val MALE_SYMBOL = "\u2642"
private const val UNKNOWN_SYMBOL = "?"


class CatViewHolder(
    private val containerView: View,
    private val imageLoader: ImageLoader,
    private val onClickListener: CatAdapter.OnClickListener) :
    RecyclerView.ViewHolder(containerView) {

    //containerView is the container layout of each item list
    //Here findViewById is used to get the reference of each views inside the container
    private val catBiographyView: TextView by lazy {
        containerView.findViewById(R.id.cat_biography) }
    private val catBreedView: TextView by lazy {
        containerView.findViewById(R.id.cat_breed) }
    private val catGenderView: TextView by lazy {
        containerView.findViewById(R.id.cat_gender) }
    private val catNameView: TextView by lazy {
        containerView.findViewById(R.id.cat_name) }
    private val catPhotoView: ImageView by lazy {
        containerView.findViewById(R.id.cat_photo) }

    //This function is called in the adapter to provide the binding function
    fun bindData(cat: CatModel) {
        //Override the onClickListener function
        containerView.setOnClickListener {
            //Here we are using the onClickListener passed from the Adapter
            onClickListener.onItemClick(cat)
        }

        imageLoader.loadImage(cat.imageUrl, catPhotoView)
        catNameView.text = cat.name
        catBreedView.text = when (cat.breed) {
            CatBreed.AmericanCurl -> "American Curl"
            CatBreed.BalineseJavanese -> "Balinese-Javanese"
            CatBreed.ExoticShorthair -> "Exotic Shorthair"
            else -> "Unknown"
        }
        catBiographyView.text = cat.biography
        catGenderView.text = when (cat.gender) {
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            else -> UNKNOWN_SYMBOL
        }
    }

    //Declare an onClickListener interface
    interface OnClickListener {
        fun onClick(cat: CatModel)
    }
}