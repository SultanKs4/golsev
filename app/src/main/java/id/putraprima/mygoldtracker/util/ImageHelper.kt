package id.putraprima.mygoldtracker.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import id.putraprima.mygoldtracker.GoldsApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.*

class ImageHelper {

    companion object {
        suspend fun loadImageFromStorage(path: String): Bitmap? =
                coroutineScope {
                    var bitmap: Bitmap? = null
                    try {
                        val file = File(path)
                        bitmap = withContext(Dispatchers.IO) {
                            BitmapFactory.decodeStream(FileInputStream(file))
                        }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    bitmap
                }

        suspend fun saveToInternalStorage(bitmap: Bitmap): String =
                coroutineScope {
                    val contextWrapper = ContextWrapper(GoldsApplication.applicationContext())
                    val directory = contextWrapper.getDir("profile", Context.MODE_PRIVATE)
                    val fileName = "profile.PNG"
                    val path = File(directory, fileName)
                    var fos: FileOutputStream? = null
                    try {
                        withContext(Dispatchers.IO) {
                            fos = FileOutputStream(path)
                            bitmap.compress(Bitmap.CompressFormat.PNG, 60, fos)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        try {
                            fos?.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    path.absolutePath
                }
    }
}