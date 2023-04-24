//Brenan Marenger
//This is the screen and functions for when the suer selects a book, allowing them to change
//the title/rating, also updating the db. The user may also delete the entry

package com.example.pg5

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity


class BookView: AppCompatActivity() {

    var mDB = BookDBHelper().writableDatabase

    companion object {
        fun newIntent(packageContext: Context?): Intent?{
            val i: Intent = Intent(packageContext!!, BookView::class.java)

            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_view_activity)

        var title = intent.getStringExtra("title")
        var rating = intent.getStringExtra("rating")
        var id = intent.getIntExtra("id", 0)
        println("YOU ARE AT ${title}")

        var mTitle:EditText = findViewById(R.id.book_title)
        var mRating:EditText = findViewById(R.id.book_rating)
        var mDeleteBtn:Button = findViewById(R.id.deleteBtn)

        mTitle.setText(title)
        mRating.setText(rating)

        //update listener
        mTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                updateEntry(id, mTitle.getText().toString(), mRating.getText().toString())
            }
        })
        mRating.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                updateEntry(id, mTitle.getText().toString(), mRating.getText().toString())
            }
        })

        mDeleteBtn.setOnClickListener{
            deleteEntry(id)
            finish()
        }
    }

    fun deleteEntry(id:Int?){
        mDB!!.delete(BookTable.NAME, "${BookTable.Cols.id} = ${id}", null);
    }

    fun updateEntry(id:Int?, Title:String?, Rating: String?){
        val values = ContentValues()
        values.put(BookTable.Cols.Title, Title)
        values.put(BookTable.Cols.Rating, Rating)
        mDB.update(BookTable.NAME, values, "${BookTable.Cols.id} = ${id}", null);
    }
}