package com.bluebank.composedemo.expand

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun <T> MutableSharedFlow<T>.send(scope: CoroutineScope, event: T) {
    scope.launch {
        emit(event)
    }
}