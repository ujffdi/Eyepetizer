package com.eyepetizer.main.pkg.weiget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.eyepetizer.main.pkg.R


/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/7
 * @email ujffdtfivkg@gmail.com
 * @description 首页底部的 Tab
 */
class TabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val selectedView = AppCompatImageView(context)
    private val unselectedView = AppCompatImageView(context)

    private val selectedAnimatorSet = AnimatorSet()
    private val unselectedAnimatorSet = AnimatorSet()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView)
        val unselected = typedArray.getDrawable(R.styleable.TabView_unselected_drawable)
        val selected = typedArray.getDrawable(R.styleable.TabView_selected_drawable)
        typedArray.recycle()

        selectedView.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        selectedView.setImageDrawable(selected)

        unselectedView.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        unselectedView.setImageDrawable(unselected)

        addView(selectedView)
        addView(unselectedView)

    }

    fun selected() {
        if (unselectedAnimatorSet.isRunning) {
            unselectedAnimatorSet.cancel()
        }
        // 显示的层级
        if (selectedView.z == 0F) {
            selectedView.z = 1F
        }
        if (unselectedView.z == 1F) {
            unselectedView.z = 0F
        }
        val goneAnima = ObjectAnimator.ofFloat(
            unselectedView, PROPERTY_NAME, 0F, -40F
        ).apply {
            duration = 400
            addListener(object : TabAnimatorListener() {
                override fun onAnimationEnd(animation: Animator) {
                    unselectedView.isVisible = false
                }
            })
        }

        val visibleAnima = ObjectAnimator.ofFloat(
            selectedView, PROPERTY_NAME, selectedView.height.toFloat(), 0F
        ).apply {
            duration = 300
            addListener(object : TabAnimatorListener() {
                override fun onAnimationStart(animation: Animator) {
                    selectedView.isVisible = true
                }
            })
        }
        selectedAnimatorSet.play(visibleAnima).with(goneAnima)

        selectedAnimatorSet.start()
    }

    fun unselected() {
        if (selectedAnimatorSet.isRunning) {
            selectedAnimatorSet.cancel()
        }
        val goneAnima = ObjectAnimator.ofFloat(
            selectedView, PROPERTY_NAME, 0F, selectedView.height.toFloat()
        ).apply {
            duration = 300
            addListener(object : TabAnimatorListener() {
                override fun onAnimationEnd(animation: Animator) {
                    selectedView.isVisible = false
                }
            })
        }

        val visibleAnima = ObjectAnimator.ofFloat(
            unselectedView, PROPERTY_NAME, -unselectedView.height.toFloat(), 0F
        ).apply {
            duration = 400
            addListener(object : TabAnimatorListener() {
                override fun onAnimationStart(animation: Animator) {
                    unselectedView.isVisible = true
                }
            })
        }
        unselectedAnimatorSet.play(goneAnima).with(visibleAnima)

        unselectedAnimatorSet.start()
    }

    open class TabAnimatorListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {

        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }

    }

    companion object {

        private const val PROPERTY_NAME = "translationY"

    }

}