package com.example.appiness.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BakersResponseModel(
    @SerializedName("by")
    @Expose
    var by: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("num.backers")
    @Expose
    var backersNumber: String
)