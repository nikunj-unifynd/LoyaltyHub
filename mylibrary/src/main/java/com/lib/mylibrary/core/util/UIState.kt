package com.lib.mylibrary.core.util

sealed class UIState {
    object Loading: UIState()
    data class Error(val message: String): UIState()
    data class Success<out T>(val data: T): UIState()
}