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
import coil.request.ImageRequest
import coil.request.videoFrameMicros
import com.eyepetizer.square.export.databinding.FragmentSquareBinding
import com.tongsr.base.base.BaseFragment
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
//        contentView.findViewById<ImageView>(R.id.image).loadPicture("https://i0.wp.com/www.printmag.com/wp-content/uploads/2021/02/4cbe8d_f1ed2800a49649848102c68fc5a66e53mv2.gif?resize=476%2C280&ssl=1")

//        contentView.findViewById<ImageView>(R.id.image)
//            .load("file:///android_asset/login_welcome.mp4") {
//                videoFrameMicros(1000)
//            }
        binding.image.callback = object : SVGACallback {
            override fun onPause() {
                LogUtils.e("onPause")
            }

            override fun onFinished() {
                LogUtils.e("onFinished")
            }

            override fun onRepeat() {
                LogUtils.e("onRepeat")
            }

            override fun onStep(frame: Int, percentage: Double) {
                LogUtils.e("onStep", frame, percentage)
            }
        }
        val request = ImageRequest.Builder(appContext)
            .data("https://github.com/yyued/SVGA-Samples/blob/master/posche.svga?raw=true")
//            .data("file:///android_asset/kingset.svga")
            .target { drawable ->
                // Handle the result.
                LogUtils.e(drawable is SVGADrawable)
                if (drawable is SVGADrawable) {
                    binding.image.setVideoItem(drawable.videoItem)
                    binding.image.startAnimation()
                }
            }
            .build()
        val disposable = appContext.imageLoader.enqueue(request)
    }

    override fun doBusiness() {

    }

    private fun convertAssetToFile(context: Context, assetFileName: String): File? {
        val assetManager = context.assets
        var outputFile: File? = null

        try {
            val inputStream = assetManager.open(assetFileName)

            // Create a temporary file in the app's cache directory
            val outputDir = context.cacheDir
            outputFile = File.createTempFile("temp_", null, outputDir)

            val outputStream = FileOutputStream(outputFile)

            // Copy the contents from the input stream to the output stream
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }

            // Close the streams
            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return outputFile
    }

}