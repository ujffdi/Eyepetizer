package com.tongsr.common.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
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

    private val binding by viewBinding(ViewLoadingBinding::bind)

    // 定义 ObjectAnimator 对象的全局变量，以便可以在其他方法中访问
    private var rotationAnimator1: ObjectAnimator? = null
    private var rotationAnimator2: ObjectAnimator? = null
    private var rotationAnimator3: ObjectAnimator? = null
    private var rotationAnimator4: ObjectAnimator? = null

    private val duration = 3000L
    private val interpolator = LinearInterpolator()

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelOrPauseAnimation()
    }

    /**
     * 设置旋转动画
     *
     * @param imageView 需要设置动画的 ImageView
     * @param startDelay 动画延迟播放的时间
     */
    private fun setRotationAnimation(imageView: ImageView, startDelay: Long) {
        val rotationAnim = ObjectAnimator.ofFloat(imageView, PROPERTY_NAME, 0f, 360f)
        rotationAnim.duration = duration
        rotationAnim.interpolator = interpolator
        rotationAnim.startDelay = startDelay
        rotationAnim.repeatCount = ObjectAnimator.INFINITE

        // 根据 ImageView 存储相应的 ObjectAnimator 对象
        when (imageView) {
            binding.loadingAlpha60 -> rotationAnimator1 = rotationAnim
            binding.loadingAlpha70 -> rotationAnimator2 = rotationAnim
            binding.loadingAlpha80 -> rotationAnimator3 = rotationAnim
            binding.loadingAlpha90 -> rotationAnimator4 = rotationAnim
        }
        rotationAnim.start()
    }

    fun startAnimation() {
        setRotationAnimation(binding.loadingAlpha90, 1500)
        setRotationAnimation(binding.loadingAlpha80, 1000)
        setRotationAnimation(binding.loadingAlpha70, 500)
        setRotationAnimation(binding.loadingAlpha60, 0)
    }

    /**
     * 取消或暂停动画
     */
    fun cancelOrPauseAnimation() {
        rotationAnimator1?.cancel()
        rotationAnimator2?.cancel()
        rotationAnimator3?.cancel()
        rotationAnimator4?.cancel()

        // 或者使用 pause() 方法暂停动画
        // rotationAnimator1?.pause()
        // rotationAnimator2?.pause()
        // rotationAnimator3?.pause()
        // rotationAnimator4?.pause()
    }

    companion object {
        private const val PROPERTY_NAME = "rotation"
    }

}