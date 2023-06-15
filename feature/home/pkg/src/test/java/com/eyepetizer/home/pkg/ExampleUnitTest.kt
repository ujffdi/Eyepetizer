package com.eyepetizer.home.pkg

import com.tongsr.core.util.RegexUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertTrue(RegexUtils.isEmail("12344@qq.com"))
    }
}