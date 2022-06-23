package com.lib.mylibrary.data.remote.dto


import com.google.gson.annotations.SerializedName

data class GetSpinWheelDto(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("gems")
        val gems: String,
        @SerializedName("spinWheels")
        val spinWheels: SpinWheels,
        @SerializedName("totalPoints")
        val totalPoints: String
    ) {
        data class SpinWheels(
            @SerializedName("attempts")
            val attempts: Int,
            @SerializedName("backgroundImage")
            val backgroundImage: Int,
            @SerializedName("benefitsData")
            val benefitsData: List<BenefitsData>,
            @SerializedName("canAttempt")
            val canAttempt: Int,
            @SerializedName("description")
            val description: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("pendingAttempts")
            val pendingAttempts: Int,
            @SerializedName("perDay")
            val perDay: Int,
            @SerializedName("spinPerDay")
            val spinPerDay: Int,
            @SerializedName("targeting")
            val targeting: Int,
            @SerializedName("termsAndConditions")
            val termsAndConditions: String,
            @SerializedName("totalAttempts")
            val totalAttempts: Int,
            @SerializedName("totalSpins")
            val totalSpins: Int,
            @SerializedName("totalUsed")
            val totalUsed: Int,
            @SerializedName("unlockType")
            val unlockType: Int,
            @SerializedName("unlockValue")
            val unlockValue: Int
        ) {
            data class BenefitsData(
                @SerializedName("benefitId")
                val benefitId: Int,
                @SerializedName("benefitType")
                val benefitType: Int,
                @SerializedName("couponId")
                val couponId: Int,
                @SerializedName("couponName")
                val couponName: String,
                @SerializedName("displayName")
                val displayName: String,
                @SerializedName("logo")
                val logo: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("retailerId")
                val retailerId: Int,
                @SerializedName("value")
                val value: Any
            )
        }
    }

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
}