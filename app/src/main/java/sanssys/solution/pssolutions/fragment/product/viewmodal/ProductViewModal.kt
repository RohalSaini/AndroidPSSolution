package sanssys.solution.pssolutions.fragment.product.viewmodal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import sanssys.solution.pssolutions.modal.*
import sanssys.solution.pssolutions.server.Api
import sanssys.solution.pssolutions.util.*
import javax.inject.Inject


@HiltViewModel
class ProductViewModal  @Inject constructor(
    var session: Session,
    application: Application,
    var api:Api
) :AndroidViewModel(application) {
    suspend fun login(token:String,category:String): Either<ItemResponse, Exception> {
        try {
            val call = api.type(
                token = token,
                category = category
            )
            if (call.isSuccessful) {
                val response: Either<ItemResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        if(response.a.status) {
                            return Left(response.a)
                        } else {
                            return Right(java.lang.Exception(response.a.error))
                        }
                    }
                    is Right -> {
                        return Right(Exception("server fetching error"))
                    }
                }
            }
            else {
                val message = JSONObject(call.errorBody()!!.string()).getString("error")
                return Right(Exception(message))
            }
        } catch (error: Exception) {
            Log.e("Product Error ",error.message.toString())
            return Right(error)
        }
    }

    fun onCartAdd(position: Int,item:Item) {
        var cart = session.getYourCart();
        if(session.getYourCart() == null) {
            cart = ArrayList<Item>()
            item.cart = 1
            cart.add(item)
        } else {
            var notFound = true
            for (product in cart!!) {
                if(product.id == item.id) {
                    notFound = false
                    product.cart += 1
                }
            }
            if(notFound) {
                item.cart = 1
                cart.add(item)
            }
        }
        session.saveYourCart(cart)
        /*

        if(list == null) {
            item.cart = 1
            cart.add(item)
            session.saveList(cart)
        } else {
            var Notfound = true
            for (product in list) {
                if(product.id == item.id) {
                    Notfound = false
                    product.quantity = product.quantity +1;
                }
            }
            if(Notfound) {
                item.quantity= 1
                list.add(item)
                session.saveList(list)
            } else {
                session.saveList(list)
            }
        } */
    }
    suspend fun order(order:OrderBody,token:String): Either<OrderResponse, Exception> {
        try {
            val call = api.order(
                body = order,
                token = token
            )
            if (call.isSuccessful) {
                val response: Either<OrderResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        if(response.a.status) {
                            session.saveOrder(order = response.a.data)
                            return Left(response.a)
                        } else {
                            return Right(Exception(response.a.error))
                        }
                    }
                    is Right -> {
                        Log.e("order",call.toString());
                        return Right(Exception(call.toString()))
                    }
                }
            }
            else {
                val message = JSONObject(call.errorBody()!!.string()).getString("error")
                return Right(Exception(message))
            }
        } catch (error: Exception) {
            Log.e("Product Error ",error.message.toString())
            return Right(error)
        }
    }
    suspend fun getAllByBrand(token:String,type:String): Either<ItemResponse, Exception> {
        try {
            val call = api.getByBrand(
                token = token,
                type = type
            )
            if (call.isSuccessful) {
                val response: Either<ItemResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        Log.d("Product List is  ",response.a.toString());
                        //session.saveListAll(list = response.a.data.productList)
                        return Left(response.a)
                    }
                    is Right -> {
                        return Right(Exception("server fetching error"))
                    }
                }
            }
            else {
                val message = JSONObject(call.errorBody()!!.string()).getString("error")
                return Right(Exception(message))
            }
        } catch (error: Exception) {
            Log.e("Product Error ",error.message.toString())
            return Right(error)
        }
    }
}