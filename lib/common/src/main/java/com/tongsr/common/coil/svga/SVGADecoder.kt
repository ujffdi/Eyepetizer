package com.tongsr.common.coil.svga

import coil.ImageLoader
import coil.decode.DecodeResult
import coil.decode.Decoder
import coil.decode.GifDecoder
import coil.decode.ImageSource
import coil.decode.SvgDecoder
import coil.fetch.SourceResult
import coil.request.Options
import com.tongsr.common.svga.SVGADrawable
import com.tongsr.common.svga.SVGAVideoEntity
import com.tongsr.common.svga.proto.MovieEntity
import com.tongsr.core.util.LogUtils
import kotlinx.coroutines.runInterruptible
import okio.buffer
import okio.inflate
import okio.sink
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.Inflater

/**
 * @author Tongsr
 * @date 2023/7/22
 * @description 基于 SVGA 的解码器
 */
class SVGADecoder constructor(
    private val source: ImageSource,
    private val options: Options,
) : Decoder {

    override suspend fun decode() : DecodeResult {

        LogUtils.e("解码SVGA开始")
        var videoItem: SVGAVideoEntity? = null
        inflate(source.source().buffer.readByteArray())?.let { byteArray ->
            LogUtils.e("返回SVGADrawable")
            videoItem = SVGAVideoEntity(
                MovieEntity.ADAPTER.decode(byteArray), source.file().toFile(), 0, 0
            )
        }
        return DecodeResult(
            drawable = SVGADrawable(videoItem!!), isSampled = false
        )
    }

    private fun inflate(byteArray: ByteArray): ByteArray? {
//        val inflater = Inflater()
//        inflater.setInput(byteArray, 0, byteArray.size)
//        val inflatedBytes = ByteArray(2048)
//        ByteArrayOutputStream().use { inflatedOutputStream ->
//            while (true) {
//                val count = inflater.inflate(inflatedBytes, 0, 2048)
//                if (count <= 0) {
//                    break
//                } else {
//                    inflatedOutputStream.write(inflatedBytes, 0, count)
//                }
//            }
//            inflater.end()
//            return inflatedOutputStream.toByteArray()
//        }
        val inflater = Inflater()
        inflater.setInput(byteArray, 0, byteArray.size)

        return try {
            val inflatedBytes = ByteArray(2048)
            val inflatedOutputStream = ByteArrayOutputStream()
            inflatedOutputStream.sink().buffer().use { bufferSink ->
                while (true) {
                    val count = inflater.inflate(inflatedBytes, 0, 2048)
                    if (count <= 0) {
                        break
                    } else {
                        bufferSink.write(inflatedBytes, 0, count)
                    }
                }
                inflater.end()
                bufferSink.flush()
            }
            inflatedOutputStream.toByteArray()
        } catch (e: IOException) {
            null
        }
    }

    class Factory : Decoder.Factory {

        override fun create(
            result: SourceResult, options: Options, imageLoader: ImageLoader
        ): Decoder? {
//            val isApplicable = isApplicable(result)
//            LogUtils.e(isApplicable, result.source.file())
//            if (!isApplicable) return null
            LogUtils.e("创建SVGADecoder")
            return SvgDecoder(result.source, options)
        }

        private fun isApplicable(result: SourceResult): Boolean {
            return result.source.file().toFile().isSVGAUnZipFile()
        }

        override fun equals(other: Any?) = other is GifDecoder.Factory

        override fun hashCode() = javaClass.hashCode()
    }

    companion object {
        private const val MIME_TYPE_SVG = "image/svg+xml"
        private const val DEFAULT_SIZE = 512f
        const val CSS_KEY = "coil#css"
    }

}