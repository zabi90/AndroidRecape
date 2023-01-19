package com.example.androidrecape.activities.datastore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.example.androidrecape.activities.BaseActivity
import com.example.androidrecape.databinding.ActivityExternalFileStorageBinding
import java.io.*
//https://stackoverflow.com/questions/57116335/environment-getexternalstoragedirectory-deprecated-in-api-level-29-java
class ExternalFileStorageActivity : BaseActivity() {

    private lateinit var binding: ActivityExternalFileStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_external_file_storage)
        binding = ActivityExternalFileStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar("External Storage", true)
        setClickListener()
    }

    private fun setClickListener() {
        binding.createDirButton.setOnClickListener {
            //createExternalPublicDir()
            writeIntoFile("Cool zohiab is Android Programmer")
            readFileContent()
        }
    }

    private fun writeIntoFile(content: String) {
        // I cannot excess public external directory on android 11+ if target set to android 11
       // Environment.getExternalStorageDirectory()
        var fileOutputStream: FileOutputStream? = null
        try {
            val newFile = File(getExternalFilesDir("Zohaib"), "Zohaib.txt")
            fileOutputStream = FileOutputStream(newFile)
            fileOutputStream?.write(content.toByteArray())
            fileOutputStream?.flush()
            Toast.makeText(this, "File Saved", Toast.LENGTH_SHORT).show()
        } catch (ex: FileNotFoundException) {
        } catch (ex: IOException) {
        } finally {
            fileOutputStream?.close()
        }
    }

    private fun readFileContent() {

        var fileInputStream: FileInputStream? = null

        try {
            fileInputStream = FileInputStream(File(getExternalFilesDir("Zohaib"), "Zohaib.txt"))
//            val inputStreamReader = InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
//
//            val bufferedReader = BufferedReader(inputStreamReader)
//
            val stringBuilder = StringBuilder()
//
//            var line: String = bufferedReader.readLine()
//
//            while (line != null) {
//                stringBuilder.append(line).append('\n');
//                line = bufferedReader.readLine()
//            }
            fileInputStream.bufferedReader().useLines { lines ->
                lines.fold("") { some, text ->

                    stringBuilder.append("$some\n$text").toString()
                }
            }
            Toast.makeText(this, "${stringBuilder.toString()}", Toast.LENGTH_SHORT).show()
        } catch (ex: FileNotFoundException) {
        } catch (ex: IOException) {
        } finally {
            fileInputStream?.close()
        }
    }


    private fun createExternalPublicDir() {

        val externalDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
        Toast.makeText(this, "$externalDir", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, ExternalFileStorageActivity::class.java)
        }
    }
}