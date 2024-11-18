package com.example.telephonedirectory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recyclerList
import kotlinx.android.synthetic.main.activity_main.searchView

class MainActivity : AppCompatActivity() {
    var list = ArrayList<contact>()
    lateinit var adapter : RCAdapter
    var db : ContactDatabase

    init {
        db = ContactDatabase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        addList()
         adapter = RCAdapter(list, this::itemOnClick)
        recyclerList.adapter = adapter

        val layoutmanager = LinearLayoutManager(this)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        recyclerList.layoutManager = layoutmanager

        recyclerList.addItemDecoration(DividerItemDecoration(this,layoutmanager.orientation))

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               adapter.filter.filter(newText)
                return false
            }
        }
          )


    }
    fun itemOnClick(position: Int){

        val i = Intent(this,AddNewContactActivity::class.java)
        i.putExtra("id" , adapter.filterableList.get(position).id)
        startActivityForResult(i,2)

    }

    private fun addList() {
       list = db.getAllContacts()
        list.sortedBy { it.name }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("TAG", "onActivityResult: + $resultCode")
        if (resultCode == RESULT_OK) {
             list.clear()

            list.addAll(db.getAllContacts())

          adapter.filter.filter("")
        }

    }

    fun addNewContact(view: View) {
        val i = Intent(this,AddNewContactActivity::class.java)
        startActivityForResult(i,1)

    }
}




