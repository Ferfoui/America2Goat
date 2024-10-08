package fr.ferfoui.america2goat.data.settings;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import fr.ferfoui.america2goat.data.DataStorage;
import io.reactivex.Single;

/**
 * Singleton class for managing application settings using RxDataStore.
 */
@Deprecated(forRemoval = true)
public class AppSettings implements DataStorage {

    private static AppSettings instance;
    private final RxDataStore<Preferences> dataStore;

    /**
     * Private constructor to initialize the RxDataStore.
     *
     * @param context The application context.
     */
    private AppSettings(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    /**
     * Returns the singleton instance of AppSettings.
     *
     * @param context The application context.
     * @return The singleton instance of AppSettings.
     */
    public static AppSettings getInstance(Context context) {
        if (instance == null) {
            instance = new AppSettings(context);
        }
        return instance;
    }

    /**
     * Stores a value in the data store.
     *
     * @param storageKey The key for the value to be stored.
     * @param value      The value to be stored.
     * @param <T>        The type of the value.
     */
    @Override
    public synchronized <T> void setData(Preferences.Key<T> storageKey, T value) {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(storageKey, value);
            return Single.just(mutablePreferences);
        });
    }

    /**
     * Retrieves a value from the data store.
     *
     * @param storageKey The key for the value to be retrieved.
     * @param <T>        The type of the value.
     * @return The retrieved value.
     */
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

    /**
     * Checks if a value is available in the data store.
     *
     * @param storageKey The key to check for availability.
     * @return True if the value is available, false otherwise.
     */
    @Override
    public synchronized boolean isDataAvailable(Preferences.Key<?> storageKey) {
        return dataStore.data().map(prefs -> prefs.contains(storageKey)).blockingFirst();
    }
}
