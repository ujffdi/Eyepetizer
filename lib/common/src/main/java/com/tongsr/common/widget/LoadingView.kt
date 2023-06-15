package com.tongsr.common.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tongsr.common.R
import com.tongsr.common.databinding.ViewLoadingBinding


/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/16
 * @email ujffdtfivkg@gmail.com
 * @description 加载中视图
 */
class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_loading, this)
    }

    private val rotationData = listOf(
        RotationData(R.id.loading_alpha_60, 0f, 360f),
        RotationData(R.id.loading_alpha_70, 0f, 360f),
        RotationData(R.id.loading_alpha_80, 0f, 360f),
        RotationData(R.id.loading_alpha_90, 0f, 360f)
    )

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    private val binding by viewBinding(ViewLoadingBinding::bind)

    fun start() {
//        val animatorSet = AnimatorSet()
//
//        val rotationAnim1 =
//            ObjectAnimator.ofFloat(binding.loadingAlpha60, "rotation", 0f, 360f).apply {
//                duration = 2500 // 设置动画时长，单位为毫秒
//                interpolator = LinearInterpolator() // 设置插值器为线性插值器，实现连续流畅的旋转动画
//                repeatCount = Animation.INFINITE // 设置重复次数为无限次
//            }
//        val rotationAnim2 =
//            ObjectAnimator.ofFloat(binding.loadingAlpha70, "rotation", 0f, 360f).apply {
//                duration = 2500 // 设置动画时长，单位为毫秒
//                interpolator = LinearInterpolator() // 设置插值器为线性插值器，实现连续流畅的旋转动画
//                repeatCount = Animation.INFINITE // 设置重复次数为无限次
//            }
//        val rotationAnim3 =
//            ObjectAnimator.ofFloat(binding.loadingAlpha80, "rotation", 0f, 360f).apply {
//                duration = 2500 // 设置动画时长，单位为毫秒
//                interpolator = LinearInterpolator() // 设置插值器为线性插值器，实现连续流畅的旋转动画
//                repeatCount = Animation.INFINITE // 设置重复次数为无限次
//            }
//
//        val rotationAnim4 =
//            ObjectAnimator.ofFloat(binding.loadingAlpha90, "rotation", 0f, 360f).apply {
//                duration = 2500 // 设置动画时长，单位为毫秒
//                interpolator = LinearInterpolator() // 设置插值器为线性插值器，实现连续流畅的旋转动画
//                repeatCount = Animation.INFINITE // 设置重复次数为无限次
//            }
//
//        animatorSet.playSequentially(
//            rotationAnim1,
//            rotationAnim2,
//            rotationAnim3,
//            rotationAnim4
//        )
//
//        animatorSet.start()
        startRotationAnimation(0)
    }

    fun stop() {
        binding.loadingAlpha90.clearAnimation()
        binding.loadingAlpha80.clearAnimation()
        binding.loadingAlpha70.clearAnimation()
        binding.loadingAlpha60.clearAnimation()
    }

    private fun createAnimation(view: View): ObjectAnimator {
        val rotationAnim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).apply {
            duration = 2500 // 设置动画时长，单位为毫秒
            interpolator = LinearInterpolator() // 设置插值器为线性插值器，实现连续流畅的旋转动画
            repeatCount = Animation.INFINITE // 设置重复次数为无限次

        }
        return rotationAnim
    }

    private fun startRotationAnimation(index: Int) {
        if (index >= rotationData.size) {
            // 所有动画执行完毕
            return
        }

        val rotation = rotationData[index]
        val view = findViewById<View>(rotation.viewId)

        view.animate()
            .rotation(rotation.startAngle)
            .setDuration(3000)
            .withEndAction {
                // 递归调用，执行下一个动画
                startRotationAnimation(index + 1)
            }
            .start()
    }

    private data class RotationData(val viewId: Int, val startAngle: Float, val endAngle: Float)

}