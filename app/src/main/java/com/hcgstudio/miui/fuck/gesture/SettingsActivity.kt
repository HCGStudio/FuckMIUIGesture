package com.hcgstudio.miui.fuck.gesture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.highcapable.yukihookapi.hook.factory.modulePrefs
import com.highcapable.yukihookapi.hook.xposed.prefs.data.PrefsData

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == "strong_mode") {
                    modulePrefs.putBoolean("strong_mode", sharedPreferences.getBoolean("strong_mode", false))
                }
            }
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val restart = findPreference<Preference>("restart")
            restart?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                Runtime.getRuntime().exec("su -c pkill systemui -9")
                Runtime.getRuntime().exec("su -c am force-stop com.miui.home")
                return@OnPreferenceClickListener true
            }
        }
    }
}