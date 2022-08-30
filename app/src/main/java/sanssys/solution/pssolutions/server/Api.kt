package sanssys.solution.pssolutions.server

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import sanssys.solution.pssolutions.modal.*

interface Api {
    @GET("/v1/allProducts")
    suspend fun searchAllItems(
        @Header("Authorization") token: String
    ): Response<ItemResponse>
    @GET("/v1/getByType")
    suspend fun type(
        @Header("Authorization") token: String,
        @Query("category" , encoded=true) category:String
    ): Response<ItemResponse>
    @GET("/getBybrand")
    suspend fun getByBrand(
        @Header("Authorization") token: String,
        @Query("type" , encoded=true) type:String
    ): Response<ItemResponse>

    @POST("/login")
    suspend fun login(
        @Body body: LoginBody
    ): Response<LoginResponse>

    @POST("/createUser")
    suspend fun createUser(
        @Body body: LoginBody
    ): Response<LoginResponse>

    @POST("/authenticateUser")
    suspend fun authenticateUser(
        @Body body: LoginBody
    ): Response<LoginResponse>

    @GET("/items")
    suspend fun getAllItems(
        @Header("Authorization") token: String
    ): Response<ItemResponse>

    @POST("/v1/addOrder")
    suspend fun order(
        @Header("Authorization") token: String,
        @Body body: OrderBody
    ): Response<OrderResponse>

    @GET("/v1/brands")
    suspend fun getAllBrands(
        @Header("Authorization") token: String
    ): Response<BrandResponse>

    @POST("/getOrders")
    suspend fun getOrders(
        @Header("Authorization") token: String,
        @Body body: OrderBodyType
    ): Response<OrderProduct>
}
