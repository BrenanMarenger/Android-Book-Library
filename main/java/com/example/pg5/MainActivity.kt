//Brenan Mareneger
//This app will open and copy the imported database, display each entry, and allow the user to add, change, or delete an entry.
//This database will save any changes and persist upon reloading
package com.example.pg5

import BookRecyclerViewAdapter
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    companion object{
        var mContext: Context? = null
    }

    var mDB: SQLiteDatabase? = null
    var bookArray:ArrayList<Book> = ArrayList<Book>()
    var adapter: BookRecyclerViewAdapter? = null
    var mAddBook: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = this

        val path: String = mContext!!.applicationInfo.dataDir + "/databases/bookDB.db"
        println("PATH: $path")
        if (File(path).exists())
            println("EXISTS")
        else {
            println("NOT EXISTS, CREATING")
            val assetfile: BufferedInputStream? =
                BufferedInputStream(mContext!!.assets.open("bookDB.db"))
            try {
                File(mContext!!.applicationInfo.dataDir + "/databases").mkdir()
            } catch (e: Exception) {
            }
            val dbfile: FileOutputStream? = FileOutputStream(path)
            while (true) {
                val ch: Int = assetfile!!.read()
                if (ch == -1) break
                dbfile!!.write(ch)
            }
            assetfile.close()
            dbfile!!.flush()
            dbfile.close()
        }

        mDB = BookDBHelper().writableDatabase

        populateArray()
        setUI()

        //Adding a new Book
        mAddBook = findViewById(R.id.add_Btn)
        mAddBook!!.setOnClickListener{
            mDB!!.insert (BookTable.NAME,null,getContentValues())
            populateArray()
            setUI()
        }
    }

    //CLosing Db
    override fun onDestroy()
    {
        super.onDestroy();
        //cursor.close()
        mDB!!.close()
    }

    override fun onResume(){
        super.onResume()
        populateArray()
        setUI()
    }

    //Function to simplify query searching
    fun query(where:String?, whereArgs:Array<String>?):BookCursorWrapper{
        val cursor = mDB!!.query(BookTable.NAME,null,where,whereArgs,null,null,null)
        return BookCursorWrapper(cursor)
    }

    //Grabs from db to populate array
    fun populateArray(){
        bookArray.clear()
        var cursor = query(null, null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            val stuff = cursor.data
            val book = Book()
            book.mTitle = stuff.title
            book.mRating = stuff.rating
            book.mId = stuff.id

            bookArray.add(book)
            cursor.moveToNext()
        }
    }

    //Creates a blank new book
    fun getContentValues():ContentValues{
        val values = ContentValues()
        values.put(BookTable.Cols.Title, "New Book")
        values.put(BookTable.Cols.Rating, "0")
        return values
    }

    //updates recycler view
    fun setUI(){
        //Create recycler view with data
        val recyclerView: RecyclerView = findViewById(R.id.bookList)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = BookRecyclerViewAdapter(this, bookArray)
        //adapter!!.setClickListener(this)
        recyclerView.setAdapter(adapter)
    }
}