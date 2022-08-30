package sanssys.solution.pssolutions.fragment.cart.viewmodal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import sanssys.solution.pssolutions.modal.ItemResponse
import sanssys.solution.pssolutions.server.Api
import sanssys.solution.pssolutions.util.*
import javax.inject.Inject


@HiltViewModel
class CartViewModal  @Inject constructor(
    var session: Session,
    application: Application,
    var api:Api
) :AndroidViewModel(application) {
    suspend fun login(token:String): Either<ItemResponse, Exception> {
        try {
            val call = api.getAllItems(
                token = token
            )
            if (call.isSuccessful) {
                val response: Either<ItemResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        // session.saveListAll(list = response.a.data.item)
                    }
                    is Right -> {
                        Log.e("Product Error => No Data ",call.toString())
                        return Right(Exception("no data"))
                    }
                }
                return response
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