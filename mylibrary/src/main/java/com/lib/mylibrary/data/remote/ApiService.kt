package com.lib.mylibrary.data.remote

import com.lib.mylibrary.core.util.Constants
import com.lib.mylibrary.data.remote.customnetworkadapter.NetworkResponse
import com.lib.mylibrary.data.remote.dto.BuySpinWheelDto
import com.lib.mylibrary.data.remote.dto.ErrorDto
import com.lib.mylibrary.data.remote.dto.GetSpinWheelDto
import com.lib.mylibrary.data.remote.dto.SpinWheelFeedDto
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiService {

    @GET("customers/spinWheel")
    suspend fun getSpinWheelFeed(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int = Constants.IDEAL_PAGE_SIZE
    ): NetworkResponse<SpinWheelFeedDto, ErrorDto>



    @GET("customers/spinWheel/{id}")
    suspend fun getSpinWheelById(
        @Path("id") id: String
    ): NetworkResponse<GetSpinWheelDto, ErrorDto>

    @FormUrlEncoded
    @POST("customers/spinWheel")
    suspend fun buySpinWheel(
        @Field("spinWheelId") spinWheelId: String
    ): NetworkResponse<BuySpinWheelDto, ErrorDto>

}