package sanssys.solution.pssolutions.recyclerview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.modal.Item
import sanssys.solution.pssolutions.modal.Product
import sanssys.solution.pssolutions.modal.ProductItem


class ItemRecyclerView (var context: Context, val listener: OnClickListener): RecyclerView.Adapter<ItemRecyclerView.ViewHolder>(){

    interface OnClickListener {
        fun onClick(position: Int)
        fun onCartAdd(position: Int,item:Item)
    }
    var list:ArrayList<Item> = ArrayList()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.course_name)
        var img: SimpleDraweeView = view.findViewById(R.id.course_pic)
        var btnMore: ConstraintLayout = view.findViewById(R.id.course)
        var price:TextView = view.findViewById(R.id.course_price)
        var addToCart= view.findViewById<Button>(R.id.addtoCard)
        var pack:TextView = view.findViewById(R.id.packSize)
        var our_price:TextView = view.findViewById(R.id.our_course_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.img.setImageURI("http://pssolution.co.in/images/${list[position].image}")
                holder.name.text = list[position].name

                holder.addToCart.setOnClickListener {
                    println(list[position]);
                    listener.onCartAdd(position,list[position]);
                }


                holder.pack.text = list[position].quantity
                var price = (list[position].price - list[position].discount).toString()
                var company_price = list[position].price.toString()
                var text = " MRP Rs $company_price $price"

                var length = company_price.length
                var green = price.length
                val ssb = SpannableStringBuilder(text)
                ssb.setSpan(
                StrikethroughSpan(),
              8, // start of the span (inclusive)
              8+length, // end of the span (exclusive)
             Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        ssb.setSpan(
            ForegroundColorSpan(Color.RED),
        8, // start of the span (inclusive)
        8+length, // end of the span (exclusive)
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
            ForegroundColorSpan(Color.GREEN),
            8+length+1, // start of the span (inclusive)
            8+length+1+green, // end of the span (exclusive)
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.our_price.text = ssb
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyDiffUtil(var oldList: MutableList<Item>,var newList: MutableList<Item>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.count()
        }

        override fun getNewListSize(): Int {
            return newList.count()
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if(oldItemPosition != newItemPosition) {
                return false
            }else {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition:Int):Boolean {
            return when {
                else -> true
            }
        }
    }
    fun setData(newPersonList:ArrayList<Item>) {
        val diffUtil = MyDiffUtil(list, newPersonList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(newPersonList)
        diffResults.dispatchUpdatesTo(this)
    }
}