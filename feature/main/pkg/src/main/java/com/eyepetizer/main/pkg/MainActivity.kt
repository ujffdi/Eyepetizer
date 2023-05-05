package com.eyepetizer.main.pkg

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.eyepetizer.main.export.PATH_MAIN
import com.eyepetizer.main.pkg.databinding.ActivityMainBinding
import com.therouter.router.Route
import com.tongsr.base.base.BaseActivity
import com.tongsr.core.extend.dpToPx
import com.tongsr.core.util.BarUtils
import com.tongsr.core.util.LogUtils


/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description Main
 */
@Route(path = PATH_MAIN, description = "main")
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private var show = false

    override fun initData(bundle: Bundle?) {
        BarUtils.setStatusBarLightMode(parentActivity, false)
    }

    override fun onBindLayout(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        binding = ActivityMainBinding.bind(contentView)

        binding.btn.setOnClickListener {
            if (show) {
                ObjectAnimator.ofFloat(binding.mineUnselect, "translationY", -50.dpToPx().toFloat(), 0F).apply {
                    duration = 500
                    addListener(object:Animator.AnimatorListener{
                        override fun onAnimationRepeat(animation: Animator) {
                            //动画重复
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //动画结束
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            //动画取消
                        }

                        override fun onAnimationStart(animation: Animator) {
                            //动画开始
                            LogUtils.e("onAnimationStart mineUnselect")
                            runOnUiThread {
                                binding.mineUnselect.isVisible = true
                            }
                        }
                    })
                    start()
                }

                ObjectAnimator.ofFloat(binding.mineSelect, "translationY", 0F, 40.dpToPx().toFloat()).apply {
                    duration = 500
                    addListener(object:Animator.AnimatorListener{
                        override fun onAnimationRepeat(animation: Animator) {
                            //动画重复
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //动画结束
                            binding.mineSelect.isVisible = false
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            //动画取消
                        }

                        override fun onAnimationStart(animation: Animator) {
                            //动画开始
                        }
                    })
                    start()
                }

            } else {
                ObjectAnimator.ofFloat(binding.mineUnselect, "translationY", 0F, -50.dpToPx().toFloat()).apply {
                    duration = 500
                    addListener(object:Animator.AnimatorListener{
                        override fun onAnimationRepeat(animation: Animator) {
                            //动画重复
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //动画结束
                            binding.mineUnselect.isVisible = false
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            //动画取消
                        }

                        override fun onAnimationStart(animation: Animator) {
                            //动画开始
                        }
                    })
                    start()
                }

                ObjectAnimator.ofFloat(binding.mineSelect, "translationY", 40.dpToPx().toFloat(), 0F).apply {
                    duration = 500
                    addListener(object:Animator.AnimatorListener{
                        override fun onAnimationRepeat(animation: Animator) {
                            //动画重复
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //动画结束
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            //动画取消
                        }

                        override fun onAnimationStart(animation: Animator) {
                            //动画开始
                            LogUtils.e("onAnimationStart mineSelect")
                            runOnUiThread {
                                binding.mineSelect.isVisible = true
                            }
                        }
                    })
                    start()
                }
            }
            show = show.not()
        }
    }

    override fun doBusiness() {

    }

}