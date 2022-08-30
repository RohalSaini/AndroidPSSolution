package sanssys.solution.pssolutions.fragment.searchbybrand.viewmodal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import sanssys.solution.pssolutions.modal.*
import sanssys.solution.pssolutions.server.Api
import sanssys.solution.pssolutions.util.Either
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session
import javax.inject.Inject

@HiltViewModel
class SearchByBrand  @Inject constructor(
    var session: Session,
    application: Application,
    var api: Api
) : AndroidViewModel(application) {
    suspend fun searchByBrand(token:String): Either<BrandResponse, Exception> {
        try {
            val call = api.getAllBrands(
                token = token
            )
            if (call.isSuccessful) {
                val response: Either<BrandResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        Log.e("order",response.a.toString())
                        if(response.a.status) {
                            return Left(response.a)
                        } else {
                            return  Right(Exception(response.a.error))
                        }
                    }
                    is Right -> {
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

    suspend fun getOrders(token:String,body:OrderBodyType): Either<OrderProduct, Exception> {
        try {
            val call = api.getOrders(
                token = token,
                body = body
            )
            if (call.isSuccessful) {
                val response: Either<OrderProduct, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        Log.e("order all",response.a.toString())
                        if(response.a.status) {
                            return Left(response.a)
                        } else {
                            return  Right(Exception(response.a.error))
                        }
                    }
                    is Right -> {
                        return Right(Exception(call.toString()))
                    }
                }
            }
            else {
                val message = JSONObject(call.errorBody()!!.string()).getString("error")
                return Right(Exception(message))
            }
        } catch (error: Exception) {
            Log.e("order Error ",error.message.toString())
            return Right(Exception(error.message.toString()))
        }
    }

}