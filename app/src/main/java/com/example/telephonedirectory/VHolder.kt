package com.example.telephonedirectory

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VHolder(view : View , itemclick : (position:Int) -> Unit) : RecyclerView.ViewHolder(view) {

    var imageID : ImageView
    var name : TextView
    var phoneNumber : TextView

    init {
         imageID = view.findViewById(R.id.imageView)
        name     = view.findViewById(R.id.textName)
        phoneNumber = view.findViewById(R.id.textphoneNumber)

        itemView.setOnClickListener{
            view ->
            itemclick(adapterPosition)
        }

    }

}