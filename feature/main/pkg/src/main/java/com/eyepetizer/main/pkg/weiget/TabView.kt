package com.eyepetizer.main.pkg.weiget

import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.core.view.isVisible
import com.tongsr.core.extend.dpToPx
import com.tongsr.core.util.LogUtils

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/7
 * @email ujffdtfivkg@gmail.com
 * @description
 */
class TabView {

    fun test() {
       /* binding.btn.setOnClickListener {
            if (show) {
                ObjectAnimator.ofFloat(binding.mineUnselect, "translationY", -50.dpToPx().toFloat(), 0F).apply {
                    duration = 500
                    addListener(object: Animator.AnimatorListener{
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
                    addListener(object: Animator.AnimatorListener{
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
                    addListener(object: Animator.AnimatorListener{
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
                    addListener(object: Animator.AnimatorListener{
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
        }*/
    }
}