package fr.ferfoui.america2goat.data.settings;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

public class AppSettings {

    private static AppSettings instance;
    private final RxDataStore<Preferences> dataStore;

    private AppSettings(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    public static AppSettings getInstance(Context context) {
        if (instance == null) {
            instance = new AppSettings(context);
        }
        return instance;
    }

    public void setUnitPreference(int unitPreference) {

    }

    public int getInputUnitPreference() {
        return 1;
    }

    public int getOutputUnitPreference() {
        return 2;
    }
}
