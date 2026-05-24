package com.example.lab_week_06

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_06.model.CatBreed
import com.example.lab_week_06.model.CatModel
import com.example.lab_week_06.model.Gender

class MainActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recycler_view)
    }
    private val catAdapter by lazy {
        //Glide is used here to load the images
        //Here we are passing the onClickListener function to the Adapter
        CatAdapter(layoutInflater, GlideImageLoader(this), object:
            CatAdapter.OnClickListener {
            //When this is triggered, the pop up dialog will be shown
            override fun onItemClick(cat: CatModel) = showSelectionDialog(cat)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setup the adapter for the recycler view
        recyclerView.adapter = catAdapter

        //Setup the layout manager for the recycler view
        //A layout manager is used to set the structure of the item views
        //For this tutorial, we're using the vertical linear structure
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        //Instantiate ItemTouchHelper for the swipe to delete callback and
        //attach it to the recycler view
        val itemTouchHelper = ItemTouchHelper(catAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Add data to the model list in the adapter
        catAdapter.setData(
            listOf(
                CatModel(
                    Gender.Male,
                    CatBreed.BalineseJavanese,
                    "Fred",
                    "Silent and deadly",
                    "https://cdn2.thecatapi.com/images/7dj.jpg"
                ),
                CatModel(
                    Gender.Female,
                    CatBreed.ExoticShorthair,
                    "Wilma",
                    "Cuddly assassin",
                    "https://cdn2.thecatapi.com/images/egv.jpg"
                ),
                CatModel(
                    Gender.Unknown,
                    CatBreed.AmericanCurl,
                    "Curious George",
                    "Award winning investigator",
                    "https://cdn2.thecatapi.com/images/bar.jpg"
                ),
                CatModel(
                    Gender.Male,
                    CatBreed.BalineseJavanese,
                    "Shadow",
                    "The ninja cat",
                    "https://cdn2.thecatapi.com/images/MTY3ODIyMQ.jpg"
                ),
                CatModel(
                    Gender.Female,
                    CatBreed.ExoticShorthair,
                    "Luna",
                    "Loves sleeping all day",
                    "https://cdn2.thecatapi.com/images/cqVJd5lFg.jpg"
                ),
                CatModel(
                    Gender.Male,
                    CatBreed.AmericanCurl,
                    "Simba",
                    "The king of the couch",
                    "https://cdn2.thecatapi.com/images/8ls.jpg"
                ),
                CatModel(
                    Gender.Female,
                    CatBreed.ExoticShorthair,
                    "Misty",
                    "Playful and sweet",
                    "https://cdn2.thecatapi.com/images/2oo.gif"
                ),
                CatModel(
                    Gender.Unknown,
                    CatBreed.BalineseJavanese,
                    "Whiskers",
                    "Curious troublemaker",
                    "https://cdn2.thecatapi.com/images/7dj.jpg"
                ),
                CatModel(
                    Gender.Male,
                    CatBreed.ExoticShorthair,
                    "Leo",
                    "Fast and fierce",
                    "https://cdn2.thecatapi.com/images/9ve.jpg"
                ),
                CatModel(
                    Gender.Female,
                    CatBreed.AmericanCurl,
                    "Cleo",
                    "Elegant and calm",
                    "https://cdn2.thecatapi.com/images/MTk0MzgzOA.jpg"
                )
            )
        )
    }

    //This will create a pop up dialog when one of the items from the recycler view is clicked.
    private fun showSelectionDialog(cat: CatModel) {
        AlertDialog.Builder(this)
//Set the title for the dialog
            .setTitle("Cat Selected")
//Set the message for the dialog
            .setMessage("You have selected cat ${cat.name}")
//Set if the OK button should be enabled
            .setPositiveButton("OK") { _, _ -> }.show()
    }
}