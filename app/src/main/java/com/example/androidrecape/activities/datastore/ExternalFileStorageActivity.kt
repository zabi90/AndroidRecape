package com.example.androidrecape.activities.datastore

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import com.example.androidrecape.activities.BaseActivity
import com.example.androidrecape.databinding.ActivityExternalFileStorageBinding
import com.example.androidrecape.sdk29AndUp
import java.io.*
import java.util.UUID

//https://stackoverflow.com/questions/57116335/environment-getexternalstoragedirectory-deprecated-in-api-level-29-java
class ExternalFileStorageActivity : BaseActivity() {

    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var binding: ActivityExternalFileStorageBinding

    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {

        if(writePermissionGranted){
            if(savePhotoToExternalStorage(UUID.randomUUID().toString(), it!!)){
                Toast.makeText(this, "Photo save externally", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Do not have external api permission", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_external_file_storage)
        binding = ActivityExternalFileStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar("External Storage", true)
        setClickListener()
        updateOrRequestPermission()
    }

    private fun updateOrRequestPermission() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdk29  = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest   = mutableListOf<String>()

        if(!writePermissionGranted){
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(!readPermissionGranted){
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            readPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
            writePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted
        }

        if(permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun savePhotoToExternalStorage(displayName: String, bmp : Bitmap) : Boolean{

        val imageCollection = sdk29AndUp{
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues  = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${displayName}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
            put(MediaStore.Images.Media.WIDTH, bmp.width)

        }
        return try {
            contentResolver.insert(imageCollection, contentValues)?.also { uri ->

                contentResolver.openOutputStream(uri).use { outputStream ->
                    if(!bmp.compress(Bitmap.CompressFormat.JPEG,95,outputStream)){
                            throw  IOException("Could not save bitmap")
                    }
                }
            } ?: throw  IOException("Could not create media store")
            true
        }catch (e : IOException){
            false
        }
    }
    private fun setClickListener() {
        binding.createDirButton.setOnClickListener {
            //createExternalPublicDir()
            writeIntoFile("Cool zohiab is Android Programmer")
            readFileContent()
        }

        binding.takePhoto.setOnClickListener {
            takePhoto.launch()
        }
    }


    private fun writeIntoFile(content: String) {
        // I cannot excess public external directory on android 11+ if target set to android 11
        // Environment.getExternalStorageDirectory()
        var fileOutputStream: FileOutputStream? = null
        try {
            val newFile = File(Environment.getExternalStorageDirectory(), "Zohaib.txt")
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
            fileInputStream =
                FileInputStream(File(Environment.getExternalStorageDirectory(), "Zohaib.txt"))
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