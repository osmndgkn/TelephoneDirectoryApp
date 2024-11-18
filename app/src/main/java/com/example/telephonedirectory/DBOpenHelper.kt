package com.example.telephonedirectory

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBOpenHelper(
    context: Context?,
    name   : String?,
    factory : SQLiteDatabase.CursorFactory?,
    version : Int
)  : SQLiteOpenHelper(context,name,factory,version){


    override fun onCreate(p0: SQLiteDatabase?) {
        val sql = "CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , name TEXT, phoneNumber TEXT , image TEXT )"

        p0!!.execSQL(sql)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {


    }




}