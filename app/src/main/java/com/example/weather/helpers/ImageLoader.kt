package com.example.weather.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.*
import java.util.*


object ImageLoader {

    fun loadImage(con: Context?, imgName: String?, imgView: ImageView?) {
        Picasso.get().load(Consts.IMAGE_URL+imgName+"@4x.png").into(imgView)
    }





    fun saveImageToRoom(imgView: ImageView, con: Context, pName: String): String {
        var result = ""
        val directory: File = con.getDir(Consts.DIRECTORY, Context.MODE_PRIVATE)
        val imagFIle = File(directory, "$pName.png")
        var outputStream: FileOutputStream? = null
        val image: Bitmap
        try {
            image = (imgView.getDrawable() as BitmapDrawable).bitmap
            outputStream = FileOutputStream(imagFIle)
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: FileNotFoundException) {
            Log.i("SonicImageLoader", "readImgFromRoom: " + e.message.toString())
        } catch (e: IOException) {
            Log.i("SonicImageLoader", "readImgFromRoom: " + e.message.toString())
        }
        result =
            directory.getAbsolutePath() + Consts.DELIMITER_IMAGE_PATH.toString() + pName + ".png"
        return result
    }

    fun readImgFromRoom(url: String, imageView: ImageView) {
        var fis: FileInputStream? = null
        val pathAndName: Array<String> = url.split(Consts.DELIMITER_IMAGE_PATH).toTypedArray()
        val file = File(pathAndName[0], pathAndName[1])
        try {
            fis = FileInputStream(file)
            val image = BitmapFactory.decodeStream(fis)
            imageView.setImageBitmap(image)
            fis.close()
        } catch (e: FileNotFoundException) {
            Log.i("SonicImageLoader", "readImgFromRoom: " + e.message.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}