package com.bluebank.composedemo.module

// Api
import retrofit2.http.Body
import retrofit2.http.POST

// 请求体
data class LoginReq(val account: String, val pwd: String)

// 统一基础响应
data class BaseResponse<T>(
    val code: Int,
    val msg: String,
    val data: T?
)

// 登录响应
data class LoginData(val token: String)


interface ApiService {
    @POST("api/login")
    suspend fun login(@Body req: LoginReq): BaseResponse<LoginData>
}