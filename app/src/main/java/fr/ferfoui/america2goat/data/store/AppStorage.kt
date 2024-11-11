package fr.ferfoui.america2goat.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * A class that handles data storage using DataStore.
 * This class ensures that only one instance of AppStorage is created.
 */
class AppStorage(
    context: Context,
    storageName: String
) {
    // Extension property to create a DataStore instance.
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(storageName)
    private val dataStore = context.dataStore

    /**
     * Stores a value in the DataStore.
     *
     * @param T The type of the value to be stored.
     * @param storageKey The key associated with the value.
     * @param value The value to be stored.
     */
    suspend fun <T> setData(storageKey: Preferences.Key<T>, value: T) {
        dataStore.edit { settings ->
            settings[storageKey] = value
        }
    }

    /**
     * Retrieves a value from the DataStore.
     *
     * @param T The type of the value to be retrieved.
     * @param storageKey The key associated with the value.
     * @return The value associated with the key.
     */
    fun <T> getData(storageKey: Preferences.Key<T>): Flow<T?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }.map { preferences ->
                preferences[storageKey]
            }
    }

    /**
     * Clears all data stored in the DataStore.
     */
    suspend fun clearData() {
        dataStore.edit { settings ->
            settings.clear()
        }
    }

}