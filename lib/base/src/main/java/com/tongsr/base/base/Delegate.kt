package com.tongsr.base.base

import android.content.Context
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/11/22
 * @email ujffdtfivkg@gmail.com
 * @description 将一个复杂的页面分成 n 个 Delegate。业务拆分
 */
open class Delegate constructor(containerView: View) : DefaultLifecycleObserver {

    protected val appContext: Context = containerView.context

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initView()
    }

    open fun initView() {

    }

}