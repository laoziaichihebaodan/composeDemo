package com.bluebank.composedemo.page.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluebank.composedemo.expand.send
import com.bluebank.composedemo.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _singleEvent = MutableSharedFlow<LoginSingleEvent>()
    val singleEvent: SharedFlow<LoginSingleEvent> = _singleEvent.asSharedFlow()

    fun dispatch(event: LoginEvent) {
        when (event) {
            is LoginEvent.InputAccount -> _uiState.update { it.copy(account = event.text) }
            is LoginEvent.InputPwd -> _uiState.update { it.copy(pwd = event.text) }
            LoginEvent.ClickLogin -> doLogin()
        }
    }

    private fun doLogin() {
        val state = _uiState.value
        if (state.account.isBlank()) {
            _singleEvent.send(viewModelScope,LoginSingleEvent.ShowToast("账号不能为空"))
            return
        }
        if (state.pwd.isBlank()) {
            _singleEvent.send(viewModelScope,LoginSingleEvent.ShowToast("密码不能为空"))
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repo.login(state.account, state.pwd)
            delay(2000)
            result.onSuccess {
                _singleEvent.send(viewModelScope,LoginSingleEvent.NavToMain)
            }.onFailure {
                _singleEvent.send(viewModelScope,LoginSingleEvent.ShowToast(it.message ?: "请求失败"))
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}