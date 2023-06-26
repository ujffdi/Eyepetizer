package com.tongsr.common.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tongsr.common.R
import com.tongsr.common.databinding.LoaderViewBinding


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

    /**
     * 动画时长
     */
    private val durationTime = 2000L

    /**
     * 等分值。据观察：当第一个动画执行到8/1的时候，第二个动画就启动，所以值为8
     */
    private val isometricValue = 8

    /**
     * 平均延迟时间
     */
    private val averageDelay = durationTime / isometricValue

    /**
     * 插值器
     */
    private val interpolator = LinearInterpolator()

    /**
     * 动画集合
     */
    private var animationSet = AnimatorSet()

    init {
        inflate(context, R.layout.loader_view, this)
    }

    // 一定要在 init 下面
    private val binding by viewBinding(LoaderViewBinding::bind)

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimation()
    }

    /**
     * 设置旋转动画
     *
     * @param imageView 需要设置动画的 ImageView
     * @param startDelay 动画延迟播放的时间
     */
    private fun createObjectAnimator(imageView: ImageView, startDelay: Long): ObjectAnimator {
        val rotationAnim = ObjectAnimator.ofFloat(imageView, PROPERTY_NAME, 0f, 360f)
        rotationAnim.duration = durationTime
        rotationAnim.interpolator = interpolator
        rotationAnim.startDelay = startDelay
        rotationAnim.repeatCount = ObjectAnimator.INFINITE
        return rotationAnim
    }

    fun startAnimation() {
        cancelAnimation()
        val animatorAlpha80 = createObjectAnimator(binding.loadingAlpha80, averageDelay * 3)
        val animatorAlpha70 = createObjectAnimator(binding.loadingAlpha70, averageDelay * 2)
        val animatorAlpha60 = createObjectAnimator(binding.loadingAlpha60, averageDelay)
        val animatorAlpha50 = createObjectAnimator(binding.loadingAlpha50, 0)
        animationSet.play(animatorAlpha50).with(animatorAlpha60).with(animatorAlpha70)
            .with(animatorAlpha80)
        animationSet.start()
    }

    /**
     * 取消或暂停动画
     */
    fun cancelAnimation() {
        if (animationSet.isRunning) {
            animationSet.cancel()
        }
    }

    companion object {
        private const val PROPERTY_NAME = "rotation"
    }

}