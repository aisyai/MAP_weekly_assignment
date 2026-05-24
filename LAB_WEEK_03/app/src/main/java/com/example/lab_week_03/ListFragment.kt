package com.example.lab_week_03

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class ListFragment : Fragment(){

//    private lateinit var coffeeListener: CoffeeListener

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is CoffeeListener) {
//            coffeeListener = context
//        } else {
//            throw RuntimeException("Must implement CoffeeListener")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coffeeList = listOf<View>(
            view.findViewById(R.id.affogato),
            view.findViewById(R.id.americano),
            view.findViewById(R.id.latte),
            view.findViewById(R.id.espresso),
            view.findViewById(R.id.mocha)
        )

        coffeeList.forEach{ coffee ->
            val fragmentBundle = Bundle()
            fragmentBundle.putInt(COFFEE_ID, coffee.id)
            coffee.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.coffee_id_action, fragmentBundle)
            )
        }
    }

//    override fun onClick(v: View?) {
//        v?.let { coffee ->
//            coffeeListener.onSelected(coffee.id)
//        }
//    }

    companion object {
//        @JvmStatic
//        fun newInstance() = ListFragment()
        const val COFFEE_ID = "COFFEE_ID"
    }
}
