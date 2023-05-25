package com.eyepetizer.home.export.service

import androidx.fragment.app.Fragment
import com.therouter.inject.Singleton

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/24
 * @email ujffdtfivkg@gmail.com
 * @description fragment service
 */
@Singleton
interface IFragmentService {

    fun getFragmentList(): List<Fragment>

}