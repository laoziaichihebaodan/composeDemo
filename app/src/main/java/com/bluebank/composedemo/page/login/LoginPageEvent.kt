package com.bluebank.composedemo.page.login

// 页面事件
sealed class LoginEvent {
    data class InputAccount(val text: String) : LoginEvent()
    data class InputPwd(val text: String) : LoginEvent()
    object ClickLogin : LoginEvent()
}

// UI状态
data class LoginState(
    val account: String = "",
    val pwd: String = "",
    val isLoading: Boolean = false
)

// 单次事件
sealed class LoginSingleEvent {
    data class ShowToast(val msg: String) : LoginSingleEvent()
    object NavToMain : LoginSingleEvent()
}