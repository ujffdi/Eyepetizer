package com.eyepetizer.user.export

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/21
 * @email ujffdtfivkg@gmail.com
 * @description 假数据
 */
object FakeDataManager {

    fun getTextData(page: Int, pageSize: Int): List<TextModel> {
        val items = mutableListOf<TextModel>()
        for (i in 0 until pageSize) {
            items.add(TextModel("text = $i"))
        }
        return items
    }

}


data class TextModel(val text: String)