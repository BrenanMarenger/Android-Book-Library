//Brenan Marenger
//This creates a dynamic list based on the entries of the database, sets an onlick listener to
//the corresponding book title
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pg5.Book
import com.example.pg5.BookView
import com.example.pg5.R

class BookRecyclerViewAdapter internal constructor(context: Context?, data: List<Book>) :
    RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder>() {

    private val mBookList: List<Book>
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    var mContext = context;

    init {
        mInflater = LayoutInflater.from(context)
        mBookList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.book_list_row, parent, false)
        return ViewHolder(view)
    }

    // binds data to the TextView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = mBookList[position]
        holder.myTextView.text = "${book.mId}. ${book.mTitle}"
        holder.myTextView.setOnClickListener{

            var x:Intent? = Intent(mContext, BookView::class.java)
            x?.putExtra("title", book.mTitle)
            x?.putExtra("rating", book.mRating)
            x?.putExtra("id", book.mId)
            mContext?.startActivity(x)
        }
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView

        init {
            myTextView = itemView.findViewById(R.id.bookTitle)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)

        }
    }

    override fun getItemCount(): Int {
        return mBookList.size
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}