package space.pal.sig.old.view.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import space.pal.sig.R;

import static space.pal.sig.old.service.download.DataSyncManager.DATA_SYNC_WORKER_TAG;
import static space.pal.sig.old.service.download.NotificationLaunchManager.NOTIFICATION_LAUNCH_WORKER_TAG;
import static space.pal.sig.old.service.download.ScheduleManager.scheduleDataSync;
import static space.pal.sig.old.service.download.ScheduleManager.scheduleLaunchNotification;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference.OnPreferenceChangeListener
            sBindPreferenceSummaryToValueListener =
            (preference, value) -> {
                String stringValue = value.toString();
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);
                    if (index >= 0) {
                        CharSequence entry = listPreference.getEntries()[index];
                        CharSequence entryValue = listPreference.getEntryValues()[index];
                        preference.setSummary(entry);
                        if (preference.getKey().equals(DATA_SYNC_WORKER_TAG)) {
                            scheduleDataSync(getContext(),
                                    Long.parseLong(entryValue.toString()),
                                    DATA_SYNC_WORKER_TAG);
                        } else {
                            scheduleLaunchNotification(getContext(),
                                    15,
                                    NOTIFICATION_LAUNCH_WORKER_TAG);
                        }
                    }
                } else {
                    preference.setSummary(stringValue);
                }
                return true;
            };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        Preference syncPreference = findPreference(DATA_SYNC_WORKER_TAG);
        Preference notificationPreference = findPreference(NOTIFICATION_LAUNCH_WORKER_TAG);
        bindPreferenceSummaryToValue(syncPreference);
        bindPreferenceSummaryToValue(notificationPreference);
    }

    private void bindPreferenceSummaryToValue(@Nullable Preference preference) {
        if (preference == null) return;
        preference.setOnPreferenceChangeListener(
                sBindPreferenceSummaryToValueListener);
        sBindPreferenceSummaryToValueListener
                .onPreferenceChange(preference, PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

}
