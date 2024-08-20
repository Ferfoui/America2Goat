package fr.ferfoui.america2goat.data.settings;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

public class SettingsRepository {

    private final RxDataStore<Preferences> dataStore;

    public SettingsRepository(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

}
