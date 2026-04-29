package com.bluebank.composedemo.repository

import com.bluebank.composedemo.module.ApiService
import com.bluebank.composedemo.module.LoginData
import com.bluebank.composedemo.module.LoginReq
import com.bluebank.composedemo.module.safeApi
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun login(account: String, pwd: String): Result<LoginData> {
        return safeApi { api.login(LoginReq(account, pwd)) }
    }
}