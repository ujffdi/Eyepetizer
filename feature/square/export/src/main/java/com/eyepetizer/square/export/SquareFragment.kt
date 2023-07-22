package com.eyepetizer.square.export

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import coil.decode.VideoFrameDecoder
import coil.load
import coil.request.videoFrameMicros
import com.tongsr.base.base.BaseFragment
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
    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_square

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
//        contentView.findViewById<ImageView>(R.id.image).loadPicture("https://i0.wp.com/www.printmag.com/wp-content/uploads/2021/02/4cbe8d_f1ed2800a49649848102c68fc5a66e53mv2.gif?resize=476%2C280&ssl=1")

        contentView.findViewById<ImageView>(R.id.image)
            .load("file:///android_asset/login_welcome.mp4") {
                videoFrameMicros(1000)
            }
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