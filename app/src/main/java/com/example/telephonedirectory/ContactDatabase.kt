package com.example.telephonedirectory

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class ContactDatabase(context: Context) {
    var dbOpenHelper : DBOpenHelper
    var dbContact : SQLiteDatabase? = null

    init{
        dbOpenHelper = DBOpenHelper(context,"contacts" , null, 1)
    }

    fun openDB(){

        dbContact = dbOpenHelper.writableDatabase
    }
    @SuppressLint("SuspiciousIndentation")
    fun closeDB(){
        if (dbContact != null && dbContact!!.isOpen)
        dbContact!!.close()
    }

    fun addNewContact(contact: contact){

        val cv = ContentValues()
        Log.d("TA", "addNewContact: ${contact.phoneNumber}")
        cv.put("name" , contact.name)
        cv.put("phoneNumber" , contact.phoneNumber)
        cv.put("image" , contact.image)

        openDB()
        dbContact!!.insert("contacts" , null , cv)
        closeDB()
    }

    fun updateContactRow(contact: contact){
        val cv = ContentValues()
        cv.put("name" , contact.name)
        cv.put("phoneNumber" , contact.phoneNumber)
        cv.put("image" , contact.image)

        openDB()
        dbContact!!.update("contacts", cv , "id = ?" , arrayOf(contact.id.toString()))
        closeDB()
    }

    fun deleteContact(id:Int) {
        openDB()
        dbContact!!.delete("contacts","id =?" , arrayOf(id.toString()))
        closeDB()

    }


    @SuppressLint("Range")
    fun getAllContacts() : ArrayList<contact> {

        val _contacts = ArrayList<contact>()
        openDB()
         val sql = "SELECT * FROM contacts "
        val c  = dbContact!!.rawQuery(sql,null)

        if (c.moveToFirst()) {

            var contact:contact

            do{
                contact = contact()
                contact.id = c.getInt(c.getColumnIndex("id"))
                contact.name = c.getString(c.getColumnIndex("name"))
                contact.phoneNumber = c.getString(c.getColumnIndex("phoneNumber"))
                contact.image   = c.getString(c.getColumnIndex("image"))

               // Log.d("TA", "getAllContacts:  ${contact.toString()}")
                _contacts.add(contact)

            }while (c.moveToNext())
        }
        closeDB()

        return _contacts
    }

    @SuppressLint("Range")
    fun getContacts(id:Int) : contact {
        var contact = contact()
        openDB()
        val sql = "SELECT * FROM contacts WHERE id = ?"
        val c  = dbContact!!.rawQuery(sql, arrayOf(id.toString()))

        if (c.moveToFirst()){

             contact.id = c.getInt(c.getColumnIndex("id"))
            contact.name = c.getString(c.getColumnIndex("name"))
            contact.phoneNumber = c.getString(c.getColumnIndex("phoneNumber"))
            contact.image   = c.getString(c.getColumnIndex("image"))

        }
        closeDB()
        return contact
    }
}