package com.tppa.intro.options

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.tppa.intro.R
import androidx.preference.PreferenceFragmentCompat

class OptionsFragment : PreferenceFragmentCompat(){

    private lateinit var preferences: SharedPreferences
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
        preferences = preferenceScreen.sharedPreferences
        val sharedPreferences = activity!!.application.getSharedPreferences("com.tppa.sharedpreferences", Application.MODE_PRIVATE)
        listener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
                sharedPreferences.edit().putString(key, preferences.getString(key, "")).apply()
            }
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onStop() {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
        super.onStop()
    }

    override fun onPause() {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
        super.onPause()
    }

    override fun onResume() {
        preferences.registerOnSharedPreferenceChangeListener(listener)
        super.onResume()
    }
}
