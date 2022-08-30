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
import sanssys.solution.pssolutions.modal.Product
import sanssys.solution.pssolutions.modal.ProductItem

/*
class CartItem (var context: Context, val listener: OnClickListener): RecyclerView.Adapter<CartItem.ViewHolder>(){

    interface OnClickListener {
        fun onClick(position: Int)
        fun onAddQuantity(position: Int)
        fun onMinusQuantity(position: Int)
        fun onCartAdd(position: Int)
        fun OnDelete(position: Int)
    }

    var oldList:ArrayList<ProductItem> = ArrayList()
    var allList:ArrayList<Product> = ArrayList()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.course_name)
        var img: SimpleDraweeView = view.findViewById(R.id.course_pic)
        var btnMore: ConstraintLayout = view.findViewById(R.id.course)
        var price: TextView = view.findViewById(R.id.course_price)
        var packSize: TextView = view.findViewById(R.id.packSize)
//        var ourPrice: TextView = view.findViewById(R.id.our_course_price)
        var quantity: TextView = view.findViewById(R.id.course_quantity)
        var addQuantity: ImageView = view.findViewById(R.id.addQuantity)
        var minusQuantity: ImageView = view.findViewById(R.id.MinusQuantity)
        var delete: ImageView = view.findViewById(R.id.Delete)
        //var buy: Button = view.findViewById(R.id.buy)
        //var addToCart= view.findViewById<Button>(R.id.addtoCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cartitem,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        if (oldList[position].id == 0) {
            holder.img.background = context.getDrawable(R.drawable.css)
            holder.img.setImageBitmap(getBitmapFromURL(url))
        } else if( oldList[position].id == 1) {
            holder.img.background = context.getDrawable(R.drawable.computerbasics)
        }else if( oldList[position].id == 2) {
            holder.img.background = context.getDrawable(R.drawable.wordpress)
        }else if( oldList[position].id == 3) {
            holder.img.background = context.getDrawable(R.drawable.python)
        } else {
            holder.img.background = context.getDrawable(R.drawable.css)
        } */
        for(item in allList) {
            println("Pos $position :: $item")
            println("Pos $position :: ${oldList[position]}")
            if(oldList[position].itemId == item.id) {
                val imageUri: Uri =
                    Uri.parse("https://www.ieltswale.com/images/${item.image}")
                holder.img.background = context.getDrawable(R.drawable.grocery)
                holder.img.setImageURI(imageUri)
                holder.name.text = item.name
                holder.price.text = "MRP Rs. "+oldList[position].price
                holder.quantity.text = "Quantity: " +oldList[position].quantity.toString()
                //holder.ourPrice.text = "Rs" + (oldList[position].price - (oldList[position].discount/100)*(oldList[position].price));
                //holder.packSize.text ="Packed Size in 1kg,2K"
               // holder.pack.text = " ${oldList[position].size } ${item.measurement} ";
                var price = (oldList[position].price - (oldList[position].discount/100)*(oldList[position].price)).toString()
                var company_price = oldList[position].price.toString()
                var text = " MRP Rs $company_price $price"

                var length = company_price.length
                val ssb = SpannableStringBuilder(text)
                ssb.setSpan(
                    StrikethroughSpan(),
                    8, // start of the span (inclusive)
                    9+length-1, // end of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                holder.price.text = ssb

                holder.packSize.text = "Stock in ${oldList[position].size } ${item.measurement} ";
                holder.addQuantity.setOnClickListener {
                    listener.onAddQuantity(position=position)
                }

                holder.minusQuantity.setOnClickListener {
                    if(position == 1) {
                        Snackbar.make(it,"Item should not be less than 1",Snackbar.LENGTH_SHORT).show()
                    }else {
                        listener.onMinusQuantity(position =position)
                    }

                }
                holder.delete.setOnClickListener {
                    listener.OnDelete(position=position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    class MyDiffUtil(var oldList: MutableList<ProductItem>, var newList: MutableList<ProductItem>) : DiffUtil.Callback() {
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
    fun setData(newPersonList:ArrayList<ProductItem>,list:ArrayList<Product>) {
        val diffUtil = MyDiffUtil(oldList, newPersonList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldList.clear()
        oldList.addAll(newPersonList)
        allList.clear()
        allList.addAll(list)
        diffResults.dispatchUpdatesTo(this)
    }
} */
class CartItem (var context: Context, val listener: OnClickListener): RecyclerView.Adapter<CartItem.ViewHolder>(){

    interface OnClickListener {
        fun onClick(position: Int)
        fun onAddQuantity(position: Int)
        fun onMinusQuantity(position: Int)
        fun onCartAdd(position: Int)
        fun OnDelete(position: Int)
    }

    var cartList:ArrayList<Item> = ArrayList()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.course_name)
        var img: SimpleDraweeView = view.findViewById(R.id.course_pic)
        var btnMore: ConstraintLayout = view.findViewById(R.id.course)
        var price: TextView = view.findViewById(R.id.course_price)
        var packSize: TextView = view.findViewById(R.id.packSize)
        var quantity: TextView = view.findViewById(R.id.course_quantity)
        var addQuantity: ImageView = view.findViewById(R.id.addQuantity)
        var minusQuantity: ImageView = view.findViewById(R.id.MinusQuantity)
        var delete: ImageView = view.findViewById(R.id.Delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cartitem,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.img.background = context.getDrawable(R.drawable.product)
                holder.img.setImageURI("http://pssolution.co.in/images/${cartList[position].image}");
                holder.name.text = cartList[position].name
                holder.price.text = "MRP Rs. "+cartList[position].price
                holder.quantity.text = "Quantity: " +cartList[position].cart.toString()
                var price = (cartList[position].price - cartList[position].discount).toString()
                var company_price = cartList[position].price.toString()
                var text = " MRP Rs $company_price $price"

                var length = company_price.length
        var green = price.length
                val ssb = SpannableStringBuilder(text)
                ssb.setSpan(
                    StrikethroughSpan(),
                    8, // start of the span (inclusive)
                    9+length-1, // end of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
        ssb.setSpan(
            ForegroundColorSpan(Color.RED),
            8, // start of the span (inclusive)
            9+length-1, // end of the span (exclusive)
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
            ForegroundColorSpan(Color.GREEN),
            8+length+1, // start of the span (inclusive)
            8+length+1+green, // end of the span (exclusive)
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

                holder.price.text = ssb

                holder.packSize.text = "Stock : ${cartList[position].quantity }";
                holder.addQuantity.setOnClickListener {
                    listener.onAddQuantity(position=position)
                }

                holder.minusQuantity.setOnClickListener {
                    if(position == 1) {
                        Snackbar.make(it,"Item should not be less than 1",Snackbar.LENGTH_SHORT).show()
                    }else {
                        listener.onMinusQuantity(position =position)
                    }

                }
                holder.delete.setOnClickListener {
                    listener.OnDelete(position=position)
                }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class MyDiffUtil(var oldList: MutableList<Item>, var newList: MutableList<Item>) : DiffUtil.Callback() {
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
    fun setData(newList:ArrayList<Item>) {
        val diffUtil = MyDiffUtil(cartList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        cartList.clear()
        cartList.addAll(newList)
        diffResults.dispatchUpdatesTo(this)
    }
}