package com.lib.mylibrary.data.remote

import com.lib.mylibrary.core.util.Constants
import com.lib.mylibrary.data.remote.customnetworkadapter.NetworkResponse
import com.lib.mylibrary.data.remote.dto.ErrorDto
import com.lib.mylibrary.data.remote.dto.SpinWheelFeedDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiService {

    @GET("customers/spinWheel")
    suspend fun getSpinWheelFeed(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int = Constants.IDEAL_PAGE_SIZE
    ): NetworkResponse<SpinWheelFeedDto, ErrorDto>


}