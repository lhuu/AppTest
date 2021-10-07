package com.example.apptest.models


import com.google.gson.annotations.SerializedName

data class itemList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val items: List<item>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)