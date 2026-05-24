package com.example.lab_week_06

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_06.model.CatModel
import com.example.lab_week_06.CatViewHolder.OnClickListener

class CatAdapter(private val layoutInflater: LayoutInflater,
                 private val imageLoader: ImageLoader,
                 private val onClickListener:OnClickListener) : RecyclerView.Adapter<CatViewHolder>() {
    //Mutable list for storing all the list data
    private val cats = mutableListOf<CatModel>()
    //A function to set the mutable list
    fun setData(newCats: List<CatModel>) {
        cats.clear()
        cats.addAll(newCats)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        cats.removeAt(position)
        notifyItemRemoved(position)
    }

    //This is used to tell the adapter that there's a data change in the mutable list
    //A view holder is used to bind the data to the layout views
    //onCreateViewHolder is instantiating the view holder it self
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CatViewHolder {
        val view = layoutInflater.inflate(R.layout.item_list, parent, false)
        return CatViewHolder(view, imageLoader, onClickListener)
    }
    //This is used to get the amount of data/item in the list
    override fun getItemCount() = cats.size
    //This is used to bind each data to each layout views
    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
    //The holder parameter stores our previously created ViewHolder
    //The holder.bindData function is declared in the CatViewHolder
        holder.bindData(cats[position])
    }

    //Declare an onClickListener interface
    interface OnClickListener {
        fun onItemClick(cat: CatModel)
    }

    //You can declare a class inside a class using the inner keyword
//Declare a class for the swipe functionality
    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        //This is used if item lists can be moved
        //Since we don't need that, we can set to false
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        //This is used to determine which directions are allowed
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) = if (viewHolder is CatViewHolder) {
            //Here, if we're not touching our phone, left and right are allowed
            makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                //Here, if we're swiping our phone, left and right are allowed
            ) or makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
            //Other gestures are not allowed (Drag, etc.)
        } else {
            0
        }
        //This is used for swipe detection
        //If a swipe is detected, then remove item
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }
    }

    //Delete Callback Instantiation
    val swipeToDeleteCallback = SwipeToDeleteCallback()

}
