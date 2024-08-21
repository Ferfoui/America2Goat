package fr.ferfoui.america2goat.data.settings;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import io.reactivex.Single;


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

    public synchronized <T> void setData(Preferences.Key<T> storageKey, T value) {
        dataStore.updateDataAsync(prefsIn -> {
            Log.d("AppSettings", "settingData: " + storageKey + " " + value);
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(storageKey, value);
            Log.d("AppSettings", "data has been set: " + storageKey + " " + value);
            return Single.just(mutablePreferences);
        });
    }

    public synchronized <T> T getData(Preferences.Key<T> storageKey) {
        Single<T> value = dataStore.data().firstOrError().map(prefs -> prefs.get(storageKey));
        try {
            return value.blockingGet();
        } catch (Exception e) {
            Log.e("AppSettings", "getData: ", e);
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean isDataAvailable(Preferences.Key<?> storageKey) {
        Log.d("AppSettings", "checking if isDataAvailable: " + storageKey);
        return dataStore.data().map(prefs -> prefs.contains(storageKey)).blockingFirst();
    }

}
