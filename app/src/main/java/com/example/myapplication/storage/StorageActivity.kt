package com.example.myapplication.storage

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStorageBinding
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class StorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

        binding.innerStorageInfo.setOnClickListener {
            getInnerDataInfo()
        }

        binding.innerStorageWrite.setOnClickListener {
            saveData()
            saveData2()
        }

        binding.innerStorageRead.setOnClickListener {
            getData()
            getData2()
        }

        binding.outerStorageInfo.setOnClickListener {
            getOuterInfo()
        }

        binding.outerStorageWrite.setOnClickListener {
            saveOuterData()
            saveOuterData2()
        }

        binding.outerStorageRead.setOnClickListener {
            getOuterData()
            getOuterData2()
        }

        binding.outerStoragePublicWrite.setOnClickListener {
            savePublicOuterData()
        }

        binding.externalStorageManagerInfo.setOnClickListener {
            externalStorageManagerInfo()
        }

        binding.mediastoreSave.setOnClickListener {
            mediaStoreSave()
        }

        binding.mediastoreQuery.setOnClickListener {
            mediastoreQuery()
        }

        binding.mediastoreDelete.setOnClickListener {
            mediastoreDelete()
        }

        binding.mediastoreUpdate.setOnClickListener {
            mediastoreUpdate()
        }

        binding.scopedStorageSaf.setOnClickListener {
            saf()
        }

        binding.requestAllFilePermission.setOnClickListener {
            requestAllFilesAccessPermission()
        }

        binding.test.setOnClickListener {
            test()
        }

        binding.test2.setOnClickListener {
            test2()
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                111)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 111 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * ????????????????????????
     */
    fun getInnerDataInfo() {
        //?????????????????????
        fileList().forEach {
            Log.e("TAG", "?????????????????????$it")
        }

        Log.e("TAG", "???????????????????????????$filesDir")
        Log.e("TAG", "?????????????????????????????????$cacheDir")
    }

    /**
     * ????????????????????????
     */
    fun saveData() {
        val content = "hello ????????????"
        val fileName = "??????????????????.txt"
        val output = openFileOutput(fileName, MODE_PRIVATE)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    /**
     * ????????????????????????
     */
    fun getData() {
        var content = ""
        val fileName = "??????????????????.txt"
        val input = openFileInput(fileName)
        val buffer = ByteArray(1024)
        var len = 0

        while ((input.read(buffer).also { len = it }) != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e("TAG", "content: $content")
    }

    /**
     * ??????????????????????????????
     */
    fun saveData2() {
        val content = "hello ??????????????????"
        val fileName = "????????????????????????.txt"
        val file = File(cacheDir, fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    /**
     * ??????????????????????????????
     */
    fun getData2() {
        var content = ""
        val fileName = "????????????????????????.txt"
        val file = File(cacheDir, fileName)
        val input = FileInputStream(file)
        val buffer = ByteArray(1024)
        var len = 0
        while ((input.read(buffer).also { len = it }) != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e("TAG", "content: $content")
    }

    /**
     * ????????????????????????
     */
    fun getOuterInfo() {
        val ret = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        Log.e("TAG", "SD??????????????????$ret")
    }

    /**
     * ??????????????????????????????
     */
    fun saveOuterData() {
        val content = "hello ????????????"
        val fileName = "??????????????????.txt"

        val file = File(getExternalFilesDir("??????"), fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    /**
     * ??????????????????????????????
     */
    fun getOuterData() {
        var content = ""
        val fileName = "??????????????????.txt"
        val file = File(getExternalFilesDir("??????"), fileName)
        val input = FileInputStream(file)
        val buffer = ByteArray(1024)
        var len = 0
        while ((input.read(buffer).also { len = it }) != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e("TAG", "content:$content")
    }

    /**
     * ????????????????????????????????????
     */
    fun saveOuterData2() {
        val content = "hello ??????????????????"
        val fileName = "????????????????????????.txt"

        val file = File(externalCacheDir, fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    /**
     * ????????????????????????????????????
     */
    fun getOuterData2() {
        var content = ""
        val fileName = "????????????????????????.txt"
        val file = File(externalCacheDir, fileName)
        val input = FileInputStream(file)
        val buffer = ByteArray(1024)
        var len = 0
        while ((input.read(buffer).also { len = it }) != -1) {
            val str = String(buffer, 0, len)
            content += str
        }
        input.close()
        Log.e("TAG", "content:$content")
    }

    /**
     * Android10??????????????????????????????
     */
    fun savePublicOuterData() {
        if (!isScopedStorage()) {
            val content = "hello ???????????????????????????"
            val fileName = "???????????????????????????.txt"

            val file = File(Environment.getExternalStorageDirectory(), fileName)
            val output = FileOutputStream(file)
            output.write(content.toByteArray())
            output.flush()
            output.close()
        }
    }

    /**
     * ????????????????????????
     */
    fun externalStorageManagerInfo() {
//        val ret = Environment.isExternalStorageManager() //true ?????????????????????false ??????????????????
//        Log.e("TAG", "???????????????????????????$ret")
    }

    fun mediaStoreSave() {
        saveDocument()
        saveImage()
    }

    /**
     * ????????????
     */
    fun saveDocument() {
        if (isScopedStorage()) {
            val content = "Hello ???????????????????????????????????????"
            val externalUri = MediaStore.Files.getContentUri("external")
            val dirName = "??????"
            val fileName = "Hello.txt"
            val path = Environment.DIRECTORY_DOWNLOADS + File.separator + dirName
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.RELATIVE_PATH, path)
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.TITLE, fileName)
            }
            val uri: Uri? = contentResolver.insert(externalUri, contentValues)
            uri?.let {
                val output = contentResolver.openOutputStream(it)
                val bos = BufferedOutputStream(output)
                bos.write(content.toByteArray())
                bos.flush()
                bos.close()
                output?.close()
            }
        }
    }

    /**
     * ????????????
     */
    fun saveImage() {
        if (isScopedStorage()) {
            val imageName = "hello.jpg"
            val dirName = "????????????"
            val path = Environment.DIRECTORY_PICTURES + File.separator + dirName
            val contentValue = ContentValues().apply {
                put(MediaStore.Images.ImageColumns.RELATIVE_PATH, path)
                put(MediaStore.Images.ImageColumns.DISPLAY_NAME, imageName)
                put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.Images.ImageColumns.TITLE, imageName)
            }
            val uri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValue)
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            uri?.let {
                val output = contentResolver.openOutputStream(it)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                output?.close()
            }
        }
    }

    var queryUri: Uri? = null

    /**
     *????????????
     */
    fun mediastoreQuery() {
        if (isScopedStorage()) {
            val imageName = "hello.jpg"
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Images.Media.DISPLAY_NAME + "=?"
            var args = arrayOf(imageName)
            val cursor = contentResolver.query(uri, null, selection, args, null)
            cursor?.let {
                if (it.moveToFirst()) {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    queryUri = ContentUris.withAppendedId(uri, id)
                    binding.imageView.setImageURI(queryUri)
                    Log.e("TAG", "????????????: $queryUri")
                }
                it.close()
            }
        }
    }

    /**
     * ????????????
     */
    fun mediastoreDelete() {
        if (isScopedStorage()) {
            queryUri?.let {
                val row: Int = contentResolver.delete(it, null, null)
                Log.e("TAG", "????????????: $row")
            }
        }
    }

    fun mediastoreUpdate() {
        if (isScopedStorage()) {
            queryUri?.let {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "hello_01.jpg")
                }
                val update: Int = contentResolver.update(it, contentValues, null, null)
                Log.e("TAG", "???????????????$update")
            }
        }
    }

    /**
     * ???????????????????????????
     */
    fun saf() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 222)
    }

    fun requestAllFilesAccessPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || Environment.isExternalStorageManager()) {
            Toast.makeText(this,
                "We can access all files on external storage now",
                Toast.LENGTH_SHORT).show()
        } else {
            val builder = AlertDialog.Builder(this)
                .setTitle("Tip")
                .setMessage("We need permission to access all files on external storage")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    startActivityForResult(intent, 333)
                }
                .setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    fun test() {
        val content = "hello test123"
        val fileName = "test123.txt"

        val file = File(Environment.getExternalStorageDirectory(), fileName)
        val output = FileOutputStream(file)
        output.write(content.toByteArray())
        output.flush()
        output.close()
    }

    fun test2() {
        val fileName = "test123.txt"
        val file = File(Environment.getExternalStorageDirectory(), fileName)
        if (file.exists()) {
            var content = ""
            val input = FileInputStream(file)
            val buffer = ByteArray(1024)
            var len = 0
            while ((input.read(buffer).also { len = it }) != -1) {
                val str = String(buffer, 0, len)
                content += str
            }
            input.close()
            Log.e("TAG", "content:$content")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            222 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val uri = data.data
                    uri?.let {
                        binding.safImageView.setImageBitmap(
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        )
                    }
                }
            }
            333 -> {
                requestAllFilesAccessPermission()
            }
        }
    }
}

fun isScopedStorage(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()


