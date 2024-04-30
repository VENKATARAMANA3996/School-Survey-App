package uk.ac.tees.mad.D3574333.ui.school_preference

import android.content.Context
import android.content.SharedPreferences

class SchoolPreference(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("school_preference", Context.MODE_PRIVATE)

    fun saveData(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
    }
}