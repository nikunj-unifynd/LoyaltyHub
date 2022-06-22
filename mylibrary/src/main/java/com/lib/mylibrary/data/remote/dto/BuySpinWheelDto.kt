package com.lib.mylibrary.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BuySpinWheelDto(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("benefitId")
        val benefitId: Int,
        @SerializedName("benefitType")
        val benefitType: Int,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("createdById")
        val createdById: Int,
        @SerializedName("customerId")
        val customerId: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("isActive")
        val isActive: Int,
        @SerializedName("isDeleted")
        val isDeleted: Int,
        @SerializedName("mallId")
        val mallId: String,
        @SerializedName("messageText")
        val messageText: Any,
        @SerializedName("source")
        val source: String,
        @SerializedName("spinDate")
        val spinDate: String,
        @SerializedName("spinWheelId")
        val spinWheelId: Int,
        @SerializedName("spinWheelStatus")
        val spinWheelStatus: Int,
        @SerializedName("transactionId")
        val transactionId: String,
        @SerializedName("typeId")
        val typeId: Int,
        @SerializedName("unlockDate")
        val unlockDate: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("usedDate")
        val usedDate: String,
        @SerializedName("benefit")
        val benefit: RegisterScratchCardDto.Data.BenefitShop
    )

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
}