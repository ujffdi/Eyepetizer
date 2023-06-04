package com.tongsr.test.pkg.serviceprovider

import com.eyepetizer.user.export.service.ITestPathService
import com.therouter.inject.ServiceProvider
import com.tongsr.test.export.PATH_TEST

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/4
 * @email ujffdtfivkg@gmail.com
 * @description Test Module path provider
 */
@ServiceProvider(returnType = ITestPathService::class)
fun getTestMainPath(): ITestPathService = object : ITestPathService {

    override fun getTestMainPath(): String = PATH_TEST

}