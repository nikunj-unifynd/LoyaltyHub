package com.lib.mylibrary.data.remote.dto


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterScratchCardDto(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
):Serializable{
    data class Data(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("benefitType")
        val benefitType: Long,
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
        val messageText: String,
        @SerializedName("scratchCardId")
        val scratchCardId: Int,
        @SerializedName("scratchCardStatus")
        val scratchCardStatus: Int,
        @SerializedName("source")
        val source: String,
        @SerializedName("transactionId")
        val transactionId: String,
        @SerializedName("typeId")
        val typeId: Int,
        @SerializedName("unlockedDate")
        val unlockedDate: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("usedDate")
        val usedDate: String,
        @SerializedName("benefit")
        val benefit: BenefitShop
    ):Serializable{
        data class BenefitShop(
            @SerializedName("name")
            val name: String,
            @SerializedName("logo")
            val logo: String,
            @SerializedName("benefitType")
            val benefitType: Long,
            @SerializedName("retailerId")
            val retailerId: Int,
            @SerializedName("retailerName")
            val retailerName: String,
            @SerializedName("transactionId")
            val transactionId: Long
        ):Serializable
    }

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    ):Serializable
}