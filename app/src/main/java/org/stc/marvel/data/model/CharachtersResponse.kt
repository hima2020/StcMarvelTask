package org.stc.marvel.data.model

import com.google.gson.annotations.SerializedName

data class CharachtersResponse(
    @SerializedName("data")
    val data: Data?,
)

data class CharacterItem(
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
)
data class Data(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("results")
    val characterItems: List<CharacterItem>?,
    @SerializedName("total")
    val total: Int?
)
data class Thumbnail(
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("path")
    val path: String?
)



















