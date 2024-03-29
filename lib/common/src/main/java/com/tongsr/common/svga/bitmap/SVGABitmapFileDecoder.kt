package com.tongsr.common.svga.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tongsr.common.svga.bitmap.SVGABitmapDecoder

/**
 * 通过文件解码 Bitmap
 *
 * Create by im_dsd 2020/7/7 17:50
 */
internal object SVGABitmapFileDecoder : SVGABitmapDecoder<String>() {

    override fun onDecode(data: String, ops: BitmapFactory.Options): Bitmap? {
        return BitmapFactory.decodeFile(data, ops)
    }
}