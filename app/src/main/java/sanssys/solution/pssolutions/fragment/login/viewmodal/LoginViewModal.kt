package sanssys.solution.pssolutions.fragment.login.viewmodal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sanssys.solution.pssolutions.modal.LoginBody
import sanssys.solution.pssolutions.modal.LoginResponse
import sanssys.solution.pssolutions.server.Api
import sanssys.solution.pssolutions.util.*
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModal  @Inject constructor(
    var session: Session,
    application: Application,
    var api:Api
) :AndroidViewModel(application) {
    suspend fun login(body: LoginBody): Either<LoginResponse, Exception> {
        try {
            val call = api.login(body)
            if (call.isSuccessful) {
                val response: Either<LoginResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        if(response.a.status) {
                            session.savePinList(response.a.options.pinlist)
                            session.saveNameList(response.a.options.names)

                            session.setLoggedIn(
                                loggedIn = true,
                                email = body.email,
                                username = body.name,
                                id = body.id,
                                password = response.a.data.token
                            )
                        } else {
                            return Right(Exception(response.a.error))
                        }
                    }
                    is Right -> {
                        return Right(Exception("no data"))
                    }
                }
                return response
            }
        } catch (error: Exception) {
            return Right(error)
        }
        return Right(Exception("no response"))
    }
    suspend fun authenticateUser(body: LoginBody): Either<LoginResponse, Exception> {
        try {
            val call = api.authenticateUser(body)
            if (call.isSuccessful) {
                val response: Either<LoginResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        if(response.a.status) {
                            session.savePinList(response.a.options.pinlist)
                            session.saveNameList(response.a.options.names)

                            session.setLoggedIn(
                                loggedIn = true,
                                email = body.email,
                                username = body.name,
                                id = body.id,
                                password = response.a.data.token
                            )
                        } else {
                            return Right(Exception(response.a.error))
                        }
                    }
                    is Right -> {
                        Log.e("error",response.b.toString())
                        return Right(Exception(response.b))
                    }
                }
                return response
            }
        } catch (error: Exception) {
            return Right(error)
        }
        return Right(Exception("no response"))
    }
    suspend fun createUser(body: LoginBody): Either<LoginResponse, Exception> {
        try {
            val call = api.createUser(body)
            if (call.isSuccessful) {
                val response: Either<LoginResponse, Exception> = Left(call.body()!!)
                when(response) {
                    is Left -> {
                        if(response.a.status) {
                            session.savePinList(response.a.options.pinlist)
                            session.saveNameList(response.a.options.names)

                            session.setLoggedIn(
                                loggedIn = true,
                                email = body.email,
                                username = body.name,
                                id = body.id,
                                password = response.a.data.token
                            )
                        } else {
                            return Right(Exception(response.a.error))
                        }
                    }
                    is Right -> {
                        return Right(Exception("no data"))
                    }
                }
                return response
            }
        } catch (error: Exception) {
            return Right(error)
        }
        return Right(Exception("no response"))
    }
}