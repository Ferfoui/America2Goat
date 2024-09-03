package fr.ferfoui.america2goat.data.settings;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import fr.ferfoui.america2goat.data.DataStorage;
import io.reactivex.Single;

public class AppSettings implements DataStorage {

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

    @Override
    public synchronized <T> void setData(Preferences.Key<T> storageKey, T value) {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(storageKey, value);
            return Single.just(mutablePreferences);
        });
    }

    @Override
    public synchronized <T> T getData(Preferences.Key<T> storageKey) {
        Single<T> value = dataStore.data().firstOrError().map(prefs -> prefs.get(storageKey));
        try {
            return value.blockingGet();
        } catch (Exception e) {
            Log.e("AppSettings", "getData: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized boolean isDataAvailable(Preferences.Key<?> storageKey) {
        return dataStore.data().map(prefs -> prefs.contains(storageKey)).blockingFirst();
    }

}
