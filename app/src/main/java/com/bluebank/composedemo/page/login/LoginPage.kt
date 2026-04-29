package com.bluebank.composedemo.page.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bluebank.composedemo.MainActivity
import com.bluebank.composedemo.R
import com.bluebank.composedemo.customui.LoadingButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginPage(
    vm: LoginViewModel = hiltViewModel(),
    onNavMain: () -> Unit
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    // 标准事件监听
    LaunchedEffect(Unit) {
        vm.singleEvent.collectLatest { event ->
            when (event) {
                is LoginSingleEvent.ShowToast -> {
                    // 替换为 全局Toast工具类
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                }
                LoginSingleEvent.NavToMain -> {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                    onNavMain()
                }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            // 生产标准：AsyncImage
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/200/200")
                    .diskCachePolicy(CachePolicy.DISABLED) // 关闭磁盘缓存
                    .memoryCachePolicy(CachePolicy.DISABLED) // 关闭内存缓存
                    .placeholder(R.mipmap.ic_launcher)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            OutlinedTextField(
                value = uiState.account,
                onValueChange = { vm.dispatch(LoginEvent.InputAccount(it)) },
                label = { Text("账号") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.pwd,
                onValueChange = { vm.dispatch(LoginEvent.InputPwd(it)) },
                label = { Text("密码") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            LoadingButton(
                text = "登录",
                loading = uiState.isLoading,
                onClick = { vm.dispatch(LoginEvent.ClickLogin) },
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}