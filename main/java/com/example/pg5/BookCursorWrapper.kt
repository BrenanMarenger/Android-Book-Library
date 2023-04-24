package com.example.pg5

import android.database.Cursor
import android.database.CursorWrapper

class BookCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    val data:BookStuff
    get(){
        val Title = getString(getColumnIndex(BookTable.Cols.Title))
        val id = getInt(getColumnIndex(BookTable.Cols.id))
        val Rating = getString(getColumnIndex(BookTable.Cols.Rating))

        return BookStuff(Title,id,Rating)
    }
}