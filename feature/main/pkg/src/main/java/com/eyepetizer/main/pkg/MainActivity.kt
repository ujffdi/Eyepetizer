package com.eyepetizer.main.pkg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.eyepetizer.main.export.PATH_MAIN
import com.therouter.router.Route
import com.tongsr.base.base.BaseActivity

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description Main 
 */
@Route(path = PATH_MAIN, description = "main")
class MainActivity : BaseActivity(){

    override fun initData(bundle: Bundle?) {
        
    }

    override fun onBindLayout(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        
    }

    override fun doBusiness() {
        
    }

    companion object {

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }

    }

}