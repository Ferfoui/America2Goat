package fr.ferfoui.america2goat.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import fr.ferfoui.america2goat.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * A class that handles data storage using DataStore.
 * This class ensures that only one instance of AppStorage is created.
 */
class AppStorage private constructor(
    private val context: Context,
    storageName: String = Constants.DATASTORE_NAME
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = storageName)

    // Make sure that the AppStorage can only be initialized once
    companion object {
        @Volatile
        private var isInitialized = false

        /**
         * Returns the single instance of AppStorage.
         * Throws an IllegalStateException if the instance has already been initialized.
         *
         * @param context The application context.
         * @return The single instance of AppStorage.
         */
        fun getInstance(context: Context, storageName: String = Constants.DATASTORE_NAME) =
            if (isInitialized) {
                throw IllegalStateException("AppStorage has already been initialized")
            } else {
                isInitialized = true
                AppStorage(context, storageName)
            }
    }

    /**
     * Stores a value in the DataStore.
     *
     * @param T The type of the value to be stored.
     * @param storageKey The key associated with the value.
     * @param value The value to be stored.
     */
    suspend fun <T> setData(storageKey: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[storageKey] = value
        }
    }

    /**
     * Retrieves a value from the DataStore.
     *
     * @param T The type of the value to be retrieved.
     * @param storageKey The key associated with the value.
     * @return The value associated with the key.
     * @throws IllegalStateException if no value is found for the key.
     */
    suspend fun <T> getData(storageKey: Preferences.Key<T>): T {
        val preferences = context.dataStore.data.first()

        val value = preferences[storageKey]
            ?: throw IllegalStateException("No value found for key $storageKey")
        return value
    }

    /**
     * Checks if a value is available in the DataStore for the given key.
     *
     * @param storageKey The key to check for availability.
     * @return True if the value is available, false otherwise.
     * @throws IllegalArgumentException if the key is null.
     */
    suspend fun isDataAvailable(storageKey: Preferences.Key<*>?): Boolean {
        val isAvailable = storageKey != null && context.dataStore.data.map { preferences ->
            preferences[storageKey] != null
        }.first()

        return isAvailable
    }

    /**
     * Clears all data stored in the DataStore.
     */
    suspend fun clearData() {
        context.dataStore.edit { settings ->
            settings.clear()
        }
    }

}