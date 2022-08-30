package sanssys.solution.pssolutions.recyclerview

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import sanssys.solution.pssolutions.R
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.snackbar.Snackbar
import sanssys.solution.pssolutions.modal.Item
import sanssys.solution.pssolutions.modal.OrderDetail
import sanssys.solution.pssolutions.modal.Product
import sanssys.solution.pssolutions.modal.ProductItem

class OrderView (var context: Context): RecyclerView.Adapter<OrderView.ViewHolder>(){
    var cartList:ArrayList<OrderDetail> = ArrayList()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var order: TextView = view.findViewById(R.id.order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = cartList[position]
        var text= ""
            text += "Order Number : ${item.orderNumber}\n"
            text += "Created At : ${item.createdAt}\n"
            text += "Bill : Rs${item.bill}\n"
            text+= "orderType : ${item.orderType}\n"
            if(item.checked) {
                text +="--------------------------\n"
                text+= "Order Status : Delivered\n"
                text +="--------------------------\n"
            } else {
                text +="--------------------------\n"
                text+= "Order Status : Pending\n"
                text +="--------------------------\n"
            }
            text +="++++++++++++++++\n"
            for (item in item.cart) {
                text +=item.name +" X "
                text += item.add
                text +="\n"
            }
            text +="++++++++++++++++"
            text +="\n\n\n\n"
        holder.order.text = text
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class MyDiffUtil(var oldList: MutableList<OrderDetail>, var newList: MutableList<OrderDetail>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.count()
        }

        override fun getNewListSize(): Int {
            return newList.count()
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition:Int):Boolean {
            return when {

                else -> true
            }
        }
    }
    fun setData(newList: ArrayList<OrderDetail>) {
        val diffUtil = MyDiffUtil(cartList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        cartList.clear()
        cartList.addAll(newList)
        diffResults.dispatchUpdatesTo(this)
    }
}