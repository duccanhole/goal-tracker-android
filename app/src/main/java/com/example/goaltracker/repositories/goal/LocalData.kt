package com.example.goaltracker.repositories.goal

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

    public fun getAll(): Array<Goal> {
        var result = emptyArray<Goal>()
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                val goal = Goal(
                    _id = jsonOject.getString("_id"),
                    name = jsonOject.getString("name"),
                    user = if (jsonOject.isNull("user")) "" else jsonOject.getString("user"),
                    isDone = jsonOject.getBoolean("isDone"),
                    hasNotification = jsonOject.getBoolean("hasNotification"),
                    notifyAt = jsonOject.getString("notifyAt"),
                    createdAt = jsonOject.getString("createdAt")
                )
                result += goal
            }
        }
        return result
    }

    public fun get(id: String): Goal? {
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                if(jsonOject.getString("_id") == id) {
                    val goal = Goal(
                        _id = jsonOject.getString("_id"),
                        name = jsonOject.getString("name"),
                        user = if (jsonOject.isNull("user")) "" else jsonOject.getString("user"),
                        isDone = jsonOject.getBoolean("isDone"),
                        hasNotification = jsonOject.getBoolean("hasNotification"),
                        notifyAt = jsonOject.getString("notifyAt"),
                        createdAt = jsonOject.getString("createdAt")
                    )
                    return goal
                }
            }
        }
        return null
    }

    public fun add(goal: Goal) {
        val jsonObject = JSONObject()
        jsonObject.apply {
            put("_id", goal._id)
            put("name", goal.name)
            put("user", goal.user)
            put("isDone", false)
            put("hasNotification", goal.hasNotification)
            put("notifyAt", goal.notifyAt)
            put("createdAt", goal.createdAt)
        }
        jsonData?.put(jsonObject)
        jsonData?.let { write(it) }
    }

    public fun add(goal: UpdateAndCreateGoal): String {
        val jsonObject = JSONObject()
        val newId = UUID.randomUUID().toString()
        jsonObject.apply {
            put("_id", newId)
            put("name", goal.name)
            put("user", goal.user)
            put("isDone", false)
            put("hasNotification", goal.hasNotification)
            put("notifyAt", goal.notifyAt)
            put("createdAt", "")
        }
        jsonData?.put(jsonObject)
        jsonData?.let { write(it) }
        return newId
    }

    public fun update(id: String, goal: Goal) {
        var index = -1
        val jsonObject = JSONObject()
        jsonObject.apply {
            put("_id", goal._id)
            put("name", goal.name)
            put("user", goal.user)
            put("isDone", goal.isDone)
            put("hasNotification", goal.hasNotification)
            put("notifyAt", goal.notifyAt)
            put("createdAt", goal.createdAt)
        }
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                if (id == jsonOject.getString("_id")) {
                    index = i;
                    break;
                }
            }
            if (index >= 0 && index < it.length()) {
                it.put(index, jsonObject)
            }
            else {
                it.put(jsonObject)
            }
            write(it)
        }
    }

    public fun update(id: String, goal: UpdateAndCreateGoal) {
        var index = -1
        val jsonObject = JSONObject()
        jsonObject.apply {
            put("_id", id)
            put("name", goal.name)
            put("user", goal.user)
            put("isDone", goal.isDone)
            put("hasNotification", goal.hasNotification)
            put("notifyAt", goal.notifyAt)
            put("createdAt", "")
        }
        jsonData?.let {
            for (i in 0 until it.length()) {
                val jsonOject = it.getJSONObject(i);
                if (id == jsonOject.getString("_id")) {
                    index = i;
                    break;
                }
            }
            if (index >= 0 && index < it.length()) {
                if(goal.name.isNullOrEmpty()) jsonObject.put("name", goal.name)
                if(goal.user.isNullOrEmpty()) jsonObject.put("user", goal.user)
                if(goal.user.isNullOrEmpty()) jsonObject.put("notifyAt", goal.notifyAt)
                jsonObject.put("isDone", goal.isDone)
                jsonObject.put("hasNotification", goal.isDone)
                it.put(index, jsonObject)
            }
            else {
                it.put(jsonObject)
            }
            write(it)
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
                write(it)
            }
        }
    }

    public fun clear() {
        write(JSONArray())
    }

    private fun read(): JSONArray? {
        return try {
            val file = File(context?.filesDir, fileName)
            val text = file.readText()
            Log.d("App", "Local goal: $text")
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