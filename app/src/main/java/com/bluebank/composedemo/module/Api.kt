package com.bluebank.composedemo.module

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApi(block: suspend () -> BaseResponse<T>): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val resp = block()
            if (resp.code == 200 && resp.data != null) {
                Result.success(resp.data)
            } else {
                Result.failure(Throwable(resp.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}