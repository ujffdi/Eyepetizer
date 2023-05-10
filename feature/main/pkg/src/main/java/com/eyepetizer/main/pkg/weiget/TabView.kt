package com.eyepetizer.main.pkg.weiget

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.eyepetizer.main.pkg.R

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/7
 * @email ujffdtfivkg@gmail.com
 * @description
 */
class TabView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView)
        val unselected = typedArray.getDrawable(R.styleable.TabView_unselected_drawable)
        val selected = typedArray.getDrawable(R.styleable.TabView_selected_drawable)
        typedArray.recycle()

        val selectedView = AppCompatImageView(context)
        selectedView.setImageDrawable(selected)

        val unselectedView = AppCompatImageView(context)
        unselectedView.setImageDrawable(unselected)

        addView(selectedView)
        addView(unselectedView)
    }

    fun switch() {

    }

    fun selected() {

    }

    fun unselected() {

    }

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