package com.example.telephonedirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_new_contact.contactText
import kotlinx.android.synthetic.main.activity_add_new_contact.deleteButton
import kotlinx.android.synthetic.main.activity_add_new_contact.ivAvatar
import kotlinx.android.synthetic.main.activity_add_new_contact.nameText
import kotlinx.android.synthetic.main.activity_add_new_contact.spinner



class AddNewContactActivity : AppCompatActivity() {

    var spinnerlist : ArrayList<String>

    init {
        //spinnerlist =   resources.getStringArray(R.array.photos).toCollection(ArrayList<String>())
        spinnerlist =   ArrayList<String>()

    }


var _contact : contact? = null
    var db : ContactDatabase

    init {
        db = ContactDatabase(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)
        prepareSpinner()

        val chatId = intent.getIntExtra("id" , -1)
        if (chatId == -1) {
              _contact = contact()
            deleteButton.visibility = View.GONE

        } else {

            _contact = db.getContacts(chatId)
            nameText.setText(_contact!!.name)
            contactText.setText(_contact!!.phoneNumber)
           val position = spinnerlist.indexOf(_contact!!.image)
            spinner.setSelection(position)
            deleteButton.visibility = View.VISIBLE

        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

              //  Log.d("TAG", "onItemSelected:  $position")
                val _identifier = resources.getIdentifier(spinnerlist.get(position), "mipmap" , packageName)
                ivAvatar.setImageResource(_identifier)
            }

        }


    }

    private fun prepareSpinner() {
        val adapter = ArrayAdapter.createFromResource(this,R.array.photos,android.R.layout.simple_spinner_item)
        spinner.adapter = adapter

        val menu = applicationContext.resources.getStringArray(R.array.photos).toList() as ArrayList<String>
        for( value in menu ) Log.d("TAG", "menuitem: ${value.toString()}")
        spinnerlist = menu
    }

    fun saveOnClick(view: View) {

        Log.d("TAG", "saveOnClick: ${contactText.text.toString()}")
        _contact!!.name = nameText.text.toString()
        _contact!!.phoneNumber = contactText.text.toString()
        _contact!!.image = spinner.selectedItem.toString()

        if (_contact!!.id == null) {
            db.addNewContact(_contact!!)
        }else {
            db.updateContactRow(_contact!!)
        }
          setResult(RESULT_OK)
        finish()
    }
    fun deleteOnClick(view: View) {
        db.deleteContact(_contact!!.id!!)
        setResult(RESULT_OK)
        finish()
    }
}