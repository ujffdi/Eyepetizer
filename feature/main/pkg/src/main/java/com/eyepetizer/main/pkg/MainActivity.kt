package com.eyepetizer.main.pkg

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.eyepetizer.main.export.PATH_MAIN
import com.eyepetizer.main.pkg.databinding.ActivityMainBinding
import com.therouter.router.Route
import com.tongsr.base.base.BaseActivity
import com.tongsr.core.util.BarUtils


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
                val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_in_down)
                slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        binding.mineUnselect.isVisible = true
                        binding.mineSelect.isVisible = false
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        
                    }

                })
                binding.mineUnselect.startAnimation(slideUpAnimation)

            } else {
                val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in_bottom)
                slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        binding.mineUnselect.isVisible = false
                        binding.mineSelect.isVisible = true
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                })
                binding.mineUnselect.startAnimation(slideUpAnimation)

            }
            show = show.not()
        }
    }

    override fun doBusiness() {

    }

}