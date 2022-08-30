package sanssys.solution.pssolutions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.content.Context

import android.widget.TextView
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.modal.PinList


class BaseAdapterPinCode(var context: Context?, private var items: ArrayList<PinList>?): BaseAdapter() {
    override fun getCount(): Int {
     return items!!.size
    }

    override fun getItem(pos: Int): PinList {
        return items!![pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = LayoutInflater.from(context).inflate(R.layout.spinner, parent, false)
        val pin =
            view.findViewById<View>(R.id.textView_pinCode) as TextView
        val currentItem: PinList = items!![position]
        pin.text = currentItem.pin.toString()
        return view
    }
}