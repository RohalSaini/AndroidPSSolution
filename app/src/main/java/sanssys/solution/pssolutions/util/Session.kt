package sanssys.solution.pssolutions.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import sanssys.solution.pssolutions.modal.*
import java.lang.reflect.Type


class Session(var ctx: Context) {
    var prefs: SharedPreferences = ctx.getSharedPreferences("grocery-app-4", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = prefs.edit()

    fun setLoggedIn(loggedIn:Boolean,email:String,password:String,username:String,id:String) {
        editor.putBoolean("loggedInMode",loggedIn)
        editor.putString("email", email)
        editor.putString("pass", password)
        editor.putString("name",username)
        editor.putString("id",id)
        editor.commit()
    }
    fun loggedIn(): Boolean {
        return prefs.getBoolean("loggedInMode", false)
    }

    fun getId(): String? {
        return prefs.getString("id","empty")
    }
    fun getPassword(): String? {
        return prefs.getString("pass","empty")
    }
    fun getEmail(): String? {
        return prefs.getString("email","empty")
    }
    fun removeAll() {
        editor.putString("orderList",null)
        prefs.edit().remove("todo-app").apply()
    }
    fun resetCart() {
        editor.putString("yourCart",null)
        editor.commit()
    }
    fun setPincode(pincode:String) {
        editor.putString("pincode",pincode)
        editor.commit()
    }
    fun getPincode(): String? {
        return prefs.getString("pincode","")
    }

    fun savePinList(list:ArrayList<PinList>) {
        val gson = Gson()
        val json = gson.toJson(list)
        println("json save pin list")
        println(json)
        editor.putString("pinList", json)
        editor.commit()
    }
    fun getPinList(): ArrayList<PinList>? {

        val gson = Gson()
        val json = prefs.getString("pinList", null)
        println("json get")
        println(json)
        val type: Type = object : TypeToken<java.util.ArrayList<PinList?>?>() {}.type
        return gson.fromJson(json, type)
    }
    fun saveNameList(list:ArrayList<Names>) {
        val gson = Gson()
        val json = gson.toJson(list)
        println("json save pin list")
        println(json)
        editor.putString("nameList", json)
        editor.commit()
    }

    fun saveOrder(order:OrderDetail) {
        val gson = Gson()
        val json = gson.toJson(order)
        println("json cart")
        println(json)
        editor.putString("order", json)
        editor.commit()
    }
    fun getOrder(): OrderDetail? {

        val gson = Gson()
        val json = prefs.getString("order", null)
        println("json get")
        println(json)
        val type: Type = object : TypeToken<OrderDetail>() {}.getType()
        return gson.fromJson(json, type)
    }

    fun setAddress(name:String,address:String,phone:String,pin:Int) {
        editor.putString("names",name)
        editor.putString("address",address)
        editor.putString("phone",phone)
        editor.putInt("pin",pin)
        editor.commit()
    }
    fun getNames(): String? {
        return prefs.getString("names",null)
    }
    fun getAdrress(): String? {
        return prefs.getString("address",null)
    }
    fun getPhone(): String? {
        return prefs.getString("phone",null)
    }
    fun saveOrderList(order:OrderResponse) {
        val gson = Gson()
        val json = gson.toJson(order)
        println("Save List ProductItem")
        println(json)
        editor.putString("orderList", json)
        editor.commit()
    }
    fun getOrderList(): OrderResponse ?{

        val gson = Gson()
        val json = prefs.getString("orderList", null)
        println("Get List ProductItem")
        println(json)
        val type: Type = object : TypeToken<OrderResponse>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveYourCart(list:ArrayList<Item>) {
        val gson = Gson()
        val json = gson.toJson(list)
        println("Save your cart List")
        println(json)
        editor.putString("yourCart", json)
        editor.commit()
    }
    fun getYourCart(): ArrayList<Item>? {
        val gson = Gson()
        val json = prefs.getString("yourCart", null)
        println("Get your cart List")
        println(json)
        val type: Type = object : TypeToken<java.util.ArrayList<Item?>?>() {}.type
        return gson.fromJson(json, type)
    }
}