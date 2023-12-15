package com.tommy.universidadesargentinaapi

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UniResponse(var message: List<Universidad>)

data class Universidad(
    var name: String,
    var country: String,
    @SerializedName("state-province") var stateProvince: String?,
    @SerializedName("alpha_two_code") var alphaCode: String,
    @SerializedName("web_pages")var webPages: List<String>,
    var domains: List<String>
): Serializable
