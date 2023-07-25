package com.eyepetizer.square.export

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.decode.VideoFrameDecoder
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.videoFrameMicros
import com.eyepetizer.square.export.databinding.FragmentSquareBinding
import com.tongsr.base.base.BaseFragment
import com.tongsr.common.coil.loadPicture
import com.tongsr.common.svga.SVGACallback
import com.tongsr.common.svga.SVGADrawable
import com.tongsr.common.svga.SVGAImageView
import com.tongsr.core.util.FileIOUtils
import com.tongsr.core.util.FileUtils
import com.tongsr.core.util.LogUtils
import com.tongsr.core.util.PathUtils
import com.tongsr.core.util.ResourceUtils
import com.tongsr.core.util.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 广场
 */
class SquareFragment : BaseFragment() {

    private val binding by viewBinding(FragmentSquareBinding::bind)

    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_square

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        /*contentView.findViewById<ImageView>(R.id.image).loadPicture("https://i0.wp.com/www.printmag.com/wp-content/uploads/2021/02/4cbe8d_f1ed2800a49649848102c68fc5a66e53mv2.gif?resize=476%2C280&ssl=1")

        contentView.findViewById<ImageView>(R.id.image)
            .load("file:///android_asset/login_welcome.mp4") {
                videoFrameMicros(1000)
            }*/

        loadSvga()

        binding.image.setOnClickListener {
            if (binding.image.isAnimating) {
                binding.image.stopAnimation()
                binding.image.clear()
            } else {
                loadSvga()
            }
        }
    }

    private fun loadSvga() {
        val request = ImageRequest.Builder(appContext)
            //.data("https://github.com/yyued/SVGA-Samples/blob/master/posche.svga?raw=true")
            .data("https://img.lamilive.com/Fpnvvuj0SldQSTDdkxw5Vn39CUFB?imageslim%7CimageView2/0/w/0/h/0/format/webp")
            //.data("file:///android_asset/kingset.svga")
            .listener { request, result ->
                LogUtils.e("request: $request, result: $result", result.diskCacheKey, result.memoryCacheKey, result.dataSource)
            }
            .target { drawable ->
                if (drawable is SVGADrawable) {
                    binding.image.setVideoItem(drawable.videoItem)
                    binding.image.startAnimation()
                }
                binding.image.setImageDrawable(drawable)
            }
            .build()
        val disposable = appContext.imageLoader.enqueue(request)
    }

    override fun doBusiness() {

    }


}