package com.tommy.universidadesargentinaapi


data class UniResponse(var message: List<Universidad>)

data class Universidad(
    var name: String,
    var country: String,
    var stateProvince: String?,
    var alphaCode: String,
    var webPages: List<String>,
    var domains: List<String>
)
