package com.apo.template.data.network.categories

import com.google.gson.annotations.SerializedName

data class CategoryJson(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("href") val apiLink: String
)