package com.tongsr.common.coil.svga

import coil.ImageLoader
import coil.decode.DecodeResult
import coil.decode.Decoder
import coil.decode.GifDecoder
import coil.decode.ImageSource
import coil.fetch.SourceResult
import coil.request.Options
import com.tongsr.common.svga.SVGADrawable
import com.tongsr.common.svga.SVGAVideoEntity
import com.tongsr.common.svga.proto.MovieEntity
import com.tongsr.core.util.LogUtils
import kotlinx.coroutines.runInterruptible
import okio.Buffer
import okio.BufferedSource
import java.io.ByteArrayOutputStream
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

    override suspend fun decode() = runInterruptible {
        LogUtils.e("解码SVGA开始", source.file().toFile().isSVGAUnZipFile())
//        val byteArray = inflate(source.source().buffer.readByteArray())
        val byteArray = bufferedSourceToByteArray(source.source())
        val inflate = inflate(byteArray)
        val movieEntity = MovieEntity.ADAPTER.decode(inflate)
        val videoItem = SVGAVideoEntity(movieEntity, source.file().toFile())
        LogUtils.e("返回SVGADrawable", movieEntity.toString())

        videoItem.prepare({
        }, null)

        DecodeResult(
            drawable = SVGADrawable(videoItem), isSampled = false
        )
    }

    fun bufferedSourceToByteArray(bufferedSource: BufferedSource): ByteArray {
        val buffer = Buffer()
        bufferedSource.readAll(buffer)
        return buffer.readByteArray()
    }

    private fun inflate(byteArray: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(byteArray, 0, byteArray.size)
        val inflatedBytes = ByteArray(2048)
        ByteArrayOutputStream().use { inflatedOutputStream ->
            while (true) {
                val count = inflater.inflate(inflatedBytes, 0, 2048)
                if (count <= 0) {
                    break
                } else {
                    inflatedOutputStream.write(inflatedBytes, 0, count)
                }
            }
            inflater.end()
            return inflatedOutputStream.toByteArray()
        }
    }

    class Factory : Decoder.Factory {

        override fun create(
            result: SourceResult, options: Options, imageLoader: ImageLoader
        ): Decoder {
            LogUtils.e("创建SVGADecoder")
            return SVGADecoder(result.source, options)
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