package com.tongsr.data.remote.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/24
 * @email ujffdtfivkg@gmail.com
 * @description WanApp List 最外层数据结构
 */

data class Paging<T>(
    val curPage: Int,
    @field: SerializedName("datas") val data: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)