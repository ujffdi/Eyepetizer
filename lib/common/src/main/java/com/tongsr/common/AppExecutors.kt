package com.tongsr.common

import android.os.Handler
import android.os.Looper
import com.tongsr.core.util.ThreadUtils
import java.util.concurrent.Executor
import java.util.concurrent.Executors
/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/1/25
 * @email ujffdtfivkg@gmail.com
 * @description 线程池
 */
object AppExecutors {

    private val mDiskIO by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val mNetworkIO by lazy {
        Executors.newFixedThreadPool(3)
    }

    private val mMainThread by lazy {
        MainThreadExecutor()
    }

    /**
     * CPU 工作线程，用户通用任务
     */
    private val mCpuThread by lazy {
        ThreadUtils.getCpuPool()
    }

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    fun cpuThread(): Executor {
        return mCpuThread
    }

    private class MainThreadExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

    }

}


