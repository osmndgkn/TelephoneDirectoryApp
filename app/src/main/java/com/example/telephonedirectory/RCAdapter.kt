package com.example.telephonedirectory

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class RCAdapter(var list : ArrayList<contact>, itemclick : (position:Int) -> Unit) : RecyclerView.Adapter<VHolder>() , Filterable {

    val filterableList = ArrayList<contact>()

    init{
        filterableList.addAll(list)
    }
    val itemclick = itemclick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {

        val v  = LayoutInflater.from(parent.context).inflate(R.layout.contactrow,parent,false)
        return VHolder(v,itemclick)
    }

    override fun getItemCount(): Int {
      return   filterableList.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {

         holder.name.text = filterableList.get(position).name
        holder.phoneNumber.text = filterableList.get(position).phoneNumber

        //Log.d("TAG", "onBindViewHolder: +${filterableList.get(position).phoneNumber} ")

        val context : Context =  holder.imageID.getContext()
         val _identifier  : Int = context.resources.getIdentifier(filterableList.get(position).image,"mipmap",context.packageName)
          // Log.d("TAG", "onBindViewHolder: + id = $_identifier   name:${filterableList.get(position).image}")
        holder.imageID.setImageResource( _identifier)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val search = p0.toString()
                filterableList.clear()

                if(search.isEmpty())  filterableList.addAll(list)


                else {
                    for( contact in list){
                     if (contact.name.toString().toLowerCase(Locale.ROOT).contains(search.toString().toLowerCase(Locale.ROOT)))
                         filterableList.add(contact)
                    }

                }
                val filterResult = FilterResults()
                filterableList.sortBy { it.name }
                filterResult.values = filterableList
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                notifyDataSetChanged()
            }


        }
    }
}