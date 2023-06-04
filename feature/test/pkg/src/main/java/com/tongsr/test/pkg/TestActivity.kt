package com.tongsr.test.pkg

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.therouter.router.Route
import com.tongsr.core.util.BarUtils
import com.tongsr.test.export.PATH_TEST

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/4
 * @email ujffdtfivkg@gmail.com
 * @description
 */
@Route(path = PATH_TEST, description = "test")
class TestActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, true)
        setContentView(R.layout.activity_test)

    }

}