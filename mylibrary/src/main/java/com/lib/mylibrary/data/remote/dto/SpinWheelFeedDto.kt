package com.lib.mylibrary.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SpinWheelFeedDto(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("gems")
        val gems: String,
        @SerializedName("spinWheels")
        val spinWheels: List<SpinWheel>,
        @SerializedName("totalEntries")
        val totalEntries: Int,
        @SerializedName("totalPages")
        val totalPages: Int,
        @SerializedName("totalPoints")
        val totalPoints: String
    ) {
        data class SpinWheel(
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
                val benefitType: Long,
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
                @SerializedName("rewardId")
                val rewardId: Int,
                @SerializedName("rewardName")
                val rewardName: String,
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