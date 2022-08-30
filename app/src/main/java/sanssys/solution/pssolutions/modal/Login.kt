package sanssys.solution.pssolutions.modal

import com.google.gson.JsonArray
import org.json.JSONArray

data class LoginBody(
    var id: String,
    var name:String,
    var email:String,
    var imageUrl:String
)

data class LoginResponse (
    var error:String,
    var success: Boolean,
    var data:Data,
    var options:Options,
    var status:Boolean
    )

data class OrderResponse (
    var data:OrderDetail,
    var options:Options,
    var status: Boolean,
    var error:String,
)

data class OrderProduct (
    var data:DataOrder,
    var status: Boolean,
    var error:String,
)

data class DataOrder (
    var orders: ArrayList<OrderDetail>
)
/*
data class OrderProduct (
    var data:orders,
    var options:Options,
    var status: Boolean,
    var error:String,
) */
data class Data (
    var email:String,
    var token:String
)

data class OrderDetail (
    var orderNumber:Int,
    var customerid: String,
    var bill: Int,
    var cart: ArrayList<Item>,
    var address: String,
    var updatedAt:String,
    var createdAt:String,
    var name:String,
    var orderType:String,
    var cell:String,
    var checked: Boolean,
)




data class Options(
    var pinlist:ArrayList<PinList>,
    var names:ArrayList<Names>
)

data class orders(
    var orders:ArrayList<OrderDetail>
)

data class PinList(
    var id:Int,
    var name:String,
    var pin:Int,
    var createdAt:String,
    var updatedAt:String
)

data class Names(
    var id:Int,
    var serialNumber:Int,
    var name:String,
    var measurement: String,
    var type:String,
    var createdAt:String,
    var updatedAt:String
)

data class OrderBody(
    var customerid: String,
    var address:String,
    var bill:Int,
    var cart: ArrayList<Item>,
    var orderType: String,
    var name:String,
    var cell:String
)
data class OrderBodyType(
    var email: String
)