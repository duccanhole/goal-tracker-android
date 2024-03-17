package com.example.myapplication.repositories.goal

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.UUID

class LocalData() {
    private val PERMISSION_REQUEST_CODE = 123
    private var context: Context? = null
    private var fileName: String = "local_data.json"

    private var jsonData: JSONArray? = null

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

    public fun get(): Array<Goal> {
        var result = emptyArray<Goal>()
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                val goal = Goal(
                    _id = jsonOject.getString("_id"),
                    name = jsonOject.getString("name"),
                    user = if (jsonOject.isNull("user")) "" else jsonOject.getString("user"),
                    isDone = jsonOject.getBoolean("isDone"),
                    hasNotfication = jsonOject.getBoolean("hasNotification"),
                    notifyAt = jsonOject.getString("notifyAt"),
                    createdAt = jsonOject.getString("createdAt")
                )
                result += goal
            }
        }
        return result
    }

    public fun add(goal: Goal) {
        val jsonObject = JSONObject()
        jsonObject.apply {
            put("_id", UUID.randomUUID().toString())
            put("name", goal.name)
            put("user", goal.user)
            put("isDone", false)
            put("hasNotification", goal.hasNotfication)
            put("notifyAt", goal.notifyAt)
            put("createdAt", goal.createdAt)
        }
        jsonData?.put(jsonObject)
        jsonData?.let { write(it) }
    }

    public fun update(goal: Goal) {
        var index = -1
        val jsonObject = JSONObject()
        jsonObject.apply {
            put("_id", goal._id)
            put("name", goal.name)
            put("user", goal.user)
            put("isDone", goal.isDone)
            put("hasNotification", goal.hasNotfication)
            put("notifyAt", goal.notifyAt)
            put("createdAt", goal.createdAt)
        }
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                if (goal._id == jsonOject.getString("_id")) {
                    index = i;
                    break;
                }
            }
            if (index >= 0 && index < it.length()) {
                it.put(index, jsonObject)
            }
        }
    }

    public fun remove(goal: Goal) {
        var index = -1
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                if (goal._id == jsonOject.getString("_id")) {
                    index = i;
                    break;
                }
            }
            if (index >= 0 && index < it.length()) {
                it.remove(index)
                write(it)
            }
        }
    }

    public fun remove(id: String) {
        var index = -1
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                if (id == jsonOject.getString("_id")) {
                    index = i;
                    break;
                }
            }
            if (index >= 0 && index < it.length()) {
                it.remove(index)
            }
        }
    }

    private fun read(): JSONArray? {
        return try {
            val file = File(context?.filesDir, fileName)
            val text = file.readText()
            Log.d("App", "read file userData: $text")
            JSONArray(text)
        } catch (e: IOException) {
            e.printStackTrace()
            return JSONArray()
        }
    }

    private fun write(jsonArray: JSONArray) {
        try {
            val file = File(context?.filesDir, fileName)
            file.writeText(jsonArray.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}