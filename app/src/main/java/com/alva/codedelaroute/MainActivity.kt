package com.alva.codedelaroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.alva.codedelaroute.navigations.AppNavigation
import com.alva.codedelaroute.repositories.SqlRepo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        copyBundledRealmFile(applicationContext.resources.openRawResource(R.raw.db), "default.realm")
        setContent {
            MyApp {
                AppNavigation()
            }
        }
    }

    fun copyBundledRealmFile(inputStream: InputStream, outFileName: String): String? {
        try {
            val file = File(this.filesDir, outFileName)
            return if (!file.exists()) {
                val outputStream = FileOutputStream(file)
                val buf = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buf).also { bytesRead = it } > 0) {
                    outputStream.write(buf, 0, bytesRead)
                }
                outputStream.close()
                file.absolutePath
            } else null
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    content()
}


