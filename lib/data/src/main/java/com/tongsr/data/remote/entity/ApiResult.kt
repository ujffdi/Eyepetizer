package com.tongsr.data.remote.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/24
 * @email ujffdtfivkg@gmail.com
 * @description WanApp 最外层数据结构
 */

data class ApiResult<T>(
    val data: T,
    @field: SerializedName("errorCode") val code: Int,
    @field: SerializedName("errorMsg") val message: String
)