package com.bluebank.composedemo.module

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeJsonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()

        // 只对指定接口返回假数据
        if (url.contains("api/login")) {
            val fakeJson = """
        {
            "code": 200,
            "msg": "登录成功",
            "data": {
                "token": "fake_test_token_123456789",
                "userId": "10086",
                "username": "测试用户"
            }
        }
        """.trimIndent()

            val mediaType = "application/json; charset=utf-8".toMediaType()
            return Response.Builder()
                .code(200)
                .message("OK")
                .body(fakeJson.toResponseBody(mediaType))
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build()
        }

        // 其他请求正常发送
        return chain.proceed(request)
    }
}