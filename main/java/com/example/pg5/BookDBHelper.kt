//Brenan Marenger
//Helps access my database, especially when making changes in another activity
package com.example.pg5

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDBHelper: SQLiteOpenHelper(MainActivity.mContext, MainActivity.mContext!!.applicationInfo.dataDir+"/databases/bookDB.db",null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}