package com.envios.kitabisa.data.remote.model


import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
) {
    data class Dates(
        @SerializedName("maximum")
        val maximum: String?,
        @SerializedName("minimum")
        val minimum: String?
    )
}