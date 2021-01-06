package space.pal.sig.view.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import space.pal.sig.R
import space.pal.sig.service.DataSyncWorker
import space.pal.sig.service.NotificationLaunchManager
import space.pal.sig.service.ScheduleManager

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 */
class SettingsFragment : PreferenceFragmentCompat() {

    private val sBindPreferenceSummaryToValueListener = Preference
            .OnPreferenceChangeListener { preference: Preference, value: Any ->
                val stringValue = value.toString()
                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(stringValue)
                    if (index >= 0) {
                        val entry = preference.entries[index]
                        val entryValue = preference.entryValues[index]
                        preference.setSummary(entry)
                        if (preference.getKey() == DataSyncWorker.DATA_SYNC_WORKER_TAG) {
                            ScheduleManager.scheduleDataSync(requireContext(),
                                    entryValue.toString().toLong())
                        } else {
                            ScheduleManager.scheduleLaunchNotification(requireContext(),
                                    15)
                        }
                    }
                } else {
                    preference.summary = stringValue
                }
                true
            }

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val syncPreference = findPreference<Preference>(DataSyncWorker.DATA_SYNC_WORKER_TAG)
        val notificationPreference = findPreference<Preference>(NotificationLaunchManager.NOTIFICATION_LAUNCH_WORKER_TAG)
        bindPreferenceSummaryToValue(syncPreference)
        bindPreferenceSummaryToValue(notificationPreference)
    }

    private fun bindPreferenceSummaryToValue(preference: Preference?) {
        if (preference == null) return
        preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener
        sBindPreferenceSummaryToValueListener
                .onPreferenceChange(preference, PreferenceManager
                        .getDefaultSharedPreferences(preference.context)
                        .getString(preference.key, ""))
    }
}