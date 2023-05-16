package com.tongsr.data.remote.entity

import com.squareup.moshi.Json

/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/24
 * @email ujffdtfivkg@gmail.com
 * @description WanApp 最外层数据结构
 */

data class ApiResult<T>(
    val data: T,
    @field: Json(name = "errorCode") val code: Int,
    @field: Json(name = "errorMsg") val message: String
)