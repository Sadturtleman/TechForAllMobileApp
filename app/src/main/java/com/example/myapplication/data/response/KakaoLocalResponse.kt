package com.example.myapplication.data.response


import com.google.gson.annotations.SerializedName

data class KakaoLocalResponse(
    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("documents")
    val documents: List<KakaoPlaceDocument>
)

data class Meta(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("is_end")
    val isEnd: Boolean,

    @SerializedName("same_name")
    val sameName: SameName?
)

data class SameName(
    @SerializedName("region")
    val region: List<String>?,

    @SerializedName("keyword")
    val keyword: String?,

    @SerializedName("selected_region")
    val selectedRegion: String?
)
