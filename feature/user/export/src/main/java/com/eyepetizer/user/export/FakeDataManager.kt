package com.eyepetizer.user.export

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/21
 * @email ujffdtfivkg@gmail.com
 * @description 假数据
 */
object FakeDataManager {

    fun getTestTextData(): List<TextBtnData> {
        val items = mutableListOf<TextBtnData>()
        for (i in 0 until 10) {
            items.add(TextBtnData("text = $i"))
        }
        return items
    }

    fun getTestBtnData(): List<TextBtnData> {
        val items = mutableListOf<TextBtnData>()
        for (i in 0 until 10) {
            items.add(TextBtnData("btn = $i"))
        }
        return items
    }

}

data class TextBtnData(val text: String)