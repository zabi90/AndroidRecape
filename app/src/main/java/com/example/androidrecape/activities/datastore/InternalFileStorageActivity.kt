package com.example.androidrecape.activities.datastore

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidrecape.activities.BaseActivity
import com.example.androidrecape.databinding.ActivityInternalFileStorageBinding
import java.io.*
import java.nio.charset.StandardCharsets

class InternalFileStorageActivity : BaseActivity() {

    private lateinit var binding: ActivityInternalFileStorageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternalFileStorageBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_internal_file_storage)
        setContentView(binding.root)
        setupActionBar("Internal File Example", true)
        binding.saveButton.setOnClickListener {

            writeIntoFile(binding.textField.editText!!.text.toString())
        }

        binding.readButton.setOnClickListener {
            readFileContent()
        }

        binding.readAssetButton.setOnClickListener {
            readAssetImage()
        }

        binding.readFileImageButton.setOnClickListener {
            readFileImage()
        }
        binding.readFileListButton.setOnClickListener {
            readFileList()
        }

        binding.createDirImageButton.setOnClickListener {
            createDir()
        }
    }

    private fun createDir(){
       val dir = getDir(CUSTOM_DIR, MODE_PRIVATE)

        val file = File(dir,"New_file.txt")

        val fileOutputStream = FileOutputStream(file)

        fileOutputStream.write("cool zohaib".toByteArray())

        fileOutputStream.flush()

        fileOutputStream.close()

        Toast.makeText(this, "Dir created successfully", Toast.LENGTH_SHORT).show()
    }

    private fun writeIntoFile(content: String) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND)
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
            fileInputStream = openFileInput(FILE_NAME)
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

                    stringBuilder.append( "$some\n$text").toString()
                }
            }
            binding.fileStatusTextView.text = stringBuilder.toString()

        } catch (ex: FileNotFoundException) {
        } catch (ex: IOException) {
        } finally {
            fileInputStream?.close()
        }
    }

    private fun readAssetImage() {
        val inputStream = assets.open("")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.assetImageView.setImageBitmap(bitmap)
    }

    private fun readFileImage() {
        var inputStream: FileInputStream? = null
        try {
             inputStream = openFileInput(LOCAL_FILE_NAME)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.assetImageView.setImageBitmap(bitmap)
        } catch (ex: FileNotFoundException) {
        } catch (ex: IOException) {
        } finally {
            inputStream?.close()
        }

    }

    private fun readFileList(){

        val fileListBuilder = StringBuilder()
        fileList().forEach {
            fileListBuilder.append(it).append("\n")
        }
        binding.fileListTextView.text = fileListBuilder.toString()
    }

    companion object {
        const val CUSTOM_DIR = "custom_dir"
        const val FILE_NAME = "android_recape.txt"
        const val LOCAL_FILE_NAME = "android_recape.txt"

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, InternalFileStorageActivity::class.java)
        }
    }
}