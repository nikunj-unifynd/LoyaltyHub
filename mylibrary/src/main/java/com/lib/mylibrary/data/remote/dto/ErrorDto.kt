package com.lib.mylibrary.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("status")
    val status: Status
) {
    class Data

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
}