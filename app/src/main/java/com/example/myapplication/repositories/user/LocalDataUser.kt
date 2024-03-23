package com.example.myapplication.repositories.user

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.json.JSONObject
import java.io.File
import java.io.IOException

class LocalDataUser() {
    private val PERMISSION_REQUEST_CODE = 123
    private var context: Context? = null
    private var fileName: String = "user_info.json"

    private var jsonData: JSONObject? = null

    constructor(context: Context) : this() {
        this.context = context
        jsonData = read()
    }

    public fun checkPermission(): Boolean {
        val permissionStatus = this.context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    public fun requestPermission() {
        val context = this.context as Activity
        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    public fun getUser(): UserData? {
        jsonData?.let {
            if(it.length() == 0) return null
            val user = UserData(username = it.getString("username"), _id = it.getString("_id"))
            return user
        }
        return null
    }

    public fun getToken(): String? {
        jsonData?.let {
            if(it.length() == 0) return null
            return it.getString("token")
        }
        return null
    }

    public fun setUser(userinfo: LoginResult) {
        try {
            val jsonObject = JSONObject()
            jsonObject.apply {
                put("token", userinfo.token)
                put("_id", userinfo.userData._id)
                put("username", userinfo.userData.username)
            }
            jsonData = jsonObject
            jsonData?.let { write(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun clear() {
        jsonData = JSONObject()
        jsonData?.let { write(it) }
    }

    private fun read(): JSONObject? {
        return try {
            val file = File(context?.filesDir, fileName)
            Log.d("App", "read file user.json")
            val text = file.readText()
            Log.d("App", "read user info userData: $text")
            JSONObject(text)
        } catch (e: IOException) {
            e.printStackTrace()
            return JSONObject()
        }
    }

    private fun write(jsonObject: JSONObject) {
        try {
            Log.d("App", "write file ${jsonObject.toString()}")
            val file = File(context?.filesDir, fileName)
            file.writeText(jsonObject.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}